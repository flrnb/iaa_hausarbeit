package de.nak.iaa.server.business.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;

import de.nak.iaa.server.business.ErgaenzungspruefungStrategie;
import de.nak.iaa.server.business.IllegalUpdateException;
import de.nak.iaa.server.business.IllegalUpdateException.IllegalPruefungsleistungException;
import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.PruefungsleistungAenderung;
import de.nak.iaa.server.business.PruefungsleistungHistoryEntry;
import de.nak.iaa.server.business.PruefungsleistungStrategie;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.entity.Dozent;
import de.nak.iaa.server.entity.ErgaenzungsPruefung;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * Implementierung von {@link PruefungService}
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
public class PruefungServiceImpl implements PruefungService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private StudentService studentService;

	@Autowired
	private PruefungsfachDAO pruefungsfachDAO;

	@Autowired
	private PruefungDAO pruefungDAO;

	@Autowired
	private PruefungsleistungDAO pruefungsleistungDAO;

	@Autowired
	private ErgaenzungspruefungStrategie ergaenzungspruefungStrategie;

	@Autowired
	private PruefungsleistungStrategie pruefungsleistungStrategie;

	@Override
	public List<Pruefungsfach> getAllPruefungsfaecher(final Manipel manipel) {
		return pruefungsfachDAO.findePruefungsfaecherFuerManipel(manipel);
	}

	@Override
	public void updatePruefungsleistungen(List<? extends PruefungsleistungAenderung> aenderungen)
			throws IllegalUpdateException {
		IllegalUpdateException exc = new IllegalUpdateException();
		for (PruefungsleistungAenderung aenderung : aenderungen) {
			Long id = aenderung.getId();
			if (!isPruefungsleistungEditable(id)) {
				Student student = pruefungsleistungDAO.findById(id, false).getStudent();
				exc.addNestedException(new IllegalPruefungsleistungException(student, getMsg(NICHT_EDITIERBAR)));
			} else
				aenderung.perform(pruefungsleistungDAO);
		}
		if (exc.getNestedExceptions().isPresent())
			throw exc;
	}

	public Pruefungsleistung addPruefungsleistung(Pruefung pruefung, Student student, Note note)
			throws IllegalPruefungsleistungException {
		Optional<Pruefungsleistung> optleistung = getLetzterVersuch(pruefung.getPruefungsfach(), student);
		Versuch nextVersuch = null;
		if (optleistung.isPresent()) {
			Pruefungsleistung letzterVersuch = optleistung.get();
			Optional<String> error = pruefungsleistungStrategie.pruefeVersuchZulaessig(letzterVersuch, pruefung);
			if (error.isPresent())
				throw new IllegalPruefungsleistungException(student, getMsg(error.get(), letzterVersuch.getNote()));
			nextVersuch = letzterVersuch.getVersuch().next().get();
		} else {
			nextVersuch = Versuch.Eins;
		}
		Pruefungsleistung neueLeistung = new Pruefungsleistung(nextVersuch, pruefung, note, student);
		return pruefungsleistungDAO.makePersistent(neueLeistung);
	}

	@Override
	public void addPruefungsleistungen(List<Triplet<Pruefung, Student, Note>> leistungen) throws IllegalUpdateException {
		IllegalUpdateException exc = new IllegalUpdateException();
		for (Triplet<Pruefung, Student, Note> leistung : leistungen) {
			Pruefung pruefung = leistung.getValue0();
			Student student = leistung.getValue1();
			Note note = leistung.getValue2();
			try {
				addPruefungsleistung(pruefung, student, note);
			} catch (IllegalPruefungsleistungException e) {
				exc.addNestedException(e);
			}
		}
		if (exc.getNestedExceptions().isPresent())
			throw exc;
	}

	@Override
	public List<Pruefungsleistung> getAllPruefungsleistungen(final Pruefungsfach fach, final Student student) {
		return getAllPruefungsleistungen(new Predicate<Pruefungsleistung>() {
			@Override
			public boolean apply(Pruefungsleistung leistung) {
				return fach.equals(leistung.getPruefung().getPruefungsfach()) && student.equals(leistung.getStudent());
			}
		});
	}

	public List<Pruefungsleistung> getAllPruefungsleistungen(Predicate<Pruefungsleistung> filter) {
		return ImmutableList.copyOf(Iterables.filter(pruefungsleistungDAO.findAll(), filter));
	}

	@Override
	public Optional<Pruefungsfach> getPruefungsfachById(Long id) {
		return Optional.fromNullable(pruefungsfachDAO.findById(id, false));
	}

	@Override
	public Optional<Pruefung> getPruefungById(Long id) {
		return Optional.fromNullable(pruefungDAO.findById(id, false));
	}

	@Override
	public List<Pruefung> getAllPruefungen(final Pruefungsfach pruefungsfach) {
		return ImmutableList.copyOf(Iterables.filter(pruefungDAO.findAll(), new Predicate<Pruefung>() {
			@Override
			public boolean apply(Pruefung p) {
				return pruefungsfach.equals(p.getPruefungsfach());
			}
		}));
	}

	@Override
	public Pruefung addPruefung(Pruefungsfach fach, Date datum, Dozent dozent) {
		Pruefung pruefung = new Pruefung(datum, fach, dozent);
		return pruefungDAO.makePersistent(pruefung);
	}

	@Override
	public boolean isPruefungsleistungEditable(Long id) {
		Pruefungsleistung leistung = pruefungsleistungDAO.findById(id, false);
		if (leistung == null)
			throw new IllegalArgumentException();
		return pruefungsleistungStrategie.isPruefungsleistungEditable(leistung) && !hasFachlichenNachfolger(leistung);
	}

	@Override
	public Map<Student, Optional<Pruefungsleistung>> getAllStudentenForPruefung(Pruefung pruefung) {
		Map<Student, Optional<Pruefungsleistung>> result = new HashMap<Student, Optional<Pruefungsleistung>>();
		for (Student student : studentService.getAllStudenten(pruefung.getPruefungsfach().getManipel()))
			result.put(student, getLetzterVersuch(pruefung.getPruefungsfach(), student));
		return ImmutableMap.copyOf(result);
	}

	@Override
	public Optional<Note> getAktuelleNote(Student student, Pruefungsfach fach) {
		return getLetzterVersuch(fach, student).transform(new Function<Pruefungsleistung, Note>() {
			@Override
			public Note apply(Pruefungsleistung leistung) {
				return pruefungsleistungStrategie.getAktuelleNote(leistung);
			}
		});
	}

	@Override
	public Map<Student, Date> getAllErgaenzungsPruefungsStudenten(Manipel manipel, Pruefungsfach fach) {
		final Map<Student, Date> result = new HashMap<Student, Date>();
		for (final Student student : studentService.getAllStudenten(manipel)) {
			getLetzterVersuch(fach, student).transform(new Function<Pruefungsleistung, Pruefungsleistung>() {
				@Override
				public Pruefungsleistung apply(Pruefungsleistung leistung) {
					if (ergaenzungspruefungStrategie.isErgaenzungsPruefungZulaessig(leistung))
						result.put(student, leistung.getPruefung().getDatum());
					return leistung;
				}
			});
		}
		return result;
	}

	@Override
	public ErgaenzungsPruefung addErgaenzungsPruefung(Student student, Pruefungsfach fach, Date datum, int prozent) {
		Optional<Pruefungsleistung> letzterVersuch = getLetzterVersuch(fach, student);
		if (!letzterVersuch.isPresent()
				|| !ergaenzungspruefungStrategie.isErgaenzungsPruefungZulaessig(letzterVersuch.get()))
			throw new IllegalStateException(KEINE_ERGAENZUNGSPRUEFUNG);
		Note note = ergaenzungspruefungStrategie.getNoteErgaenzungspruefung(prozent);
		ErgaenzungsPruefung ergaenzungsPruefung = new ErgaenzungsPruefung(note, datum);
		letzterVersuch.get().setErgaenzungsPruefung(ergaenzungsPruefung);
		pruefungsleistungDAO.makePersistent(letzterVersuch.get());
		return ergaenzungsPruefung;
	}

	@Override
	@Deprecated
	public Map<Versuch, SortedMap<Date, Pruefungsleistung>> getDeprecatedPruefungsleistungHistorie(Student student,
			Pruefungsfach fach) {
		return null;
	}

	public Map<Versuch, SortedSet<PruefungsleistungHistoryEntry>> getPruefungsleistungHistorie(Student student,
			Pruefungsfach fach) {
		Map<Versuch, SortedSet<PruefungsleistungHistoryEntry>> result = new HashMap<Versuch, SortedSet<PruefungsleistungHistoryEntry>>();
		for (Versuch versuch : Versuch.values()) {
			List<PruefungsleistungHistoryEntry> alteLeistungen = pruefungsleistungDAO.getAltePruefungsleistungen(
					student, fach, versuch);
			SortedSet<PruefungsleistungHistoryEntry> sorted = ImmutableSortedSet
					.orderedBy(new Comparator<PruefungsleistungHistoryEntry>() {
						@Override
						public int compare(PruefungsleistungHistoryEntry e1, PruefungsleistungHistoryEntry e2) {
							return e1.getGueltigVon().compareTo(e2.getGueltigVon());
						}
					}).addAll(alteLeistungen).build();
			result.put(versuch, sorted);
		}
		return result;
	}

	/**
	 * gibt für ein Prüfungsfach den letzten Versuch eines Studenten zurück
	 * 
	 * @param fach
	 * @param student
	 * @return Pruefungsleistung, falls vorhanden
	 */
	private Optional<Pruefungsleistung> getLetzterVersuch(Pruefungsfach fach, Student student) {
		Optional<Pruefungsleistung> letzteLeistung = Optional.absent();
		for (final Pruefungsleistung leistung : getAllPruefungsleistungen(fach, student)) {
			final Versuch versuch = leistung.getVersuch();
			if (!versuch.next().isPresent())
				return Optional.of(leistung);
			letzteLeistung = letzteLeistung.transform(new Function<Pruefungsleistung, Pruefungsleistung>() {
				@Override
				public Pruefungsleistung apply(Pruefungsleistung current) {
					return (current.getVersuch().toInt() < versuch.toInt()) ? leistung : current;
				}
			}).or(Optional.of(leistung));
		}
		return letzteLeistung;
	}

	/**
	 * ermittelt für eine Prüfungsleistung, ob es einen Nachfolgeversuch gibt,
	 * auch wenn dieser ggf. schon gelöscht wurde
	 * 
	 * @param leistung
	 * @return boolean
	 */
	private boolean hasFachlichenNachfolger(final Pruefungsleistung leistung) {
		return leistung.getVersuch().next().transform(new Function<Versuch, Boolean>() {
			@Override
			public Boolean apply(Versuch nextVersuch) {
				List<Pruefungsleistung> alteRevs = pruefungsleistungDAO.getVersuchFallsVorhanden(leistung.getStudent(),
						leistung.getPruefung().getPruefungsfach(), nextVersuch);
				return !alteRevs.isEmpty();
			}
		}).or(false);
	}

	private String getMsg(String code) {
		return getMsg(code, new Object[] {});
	}

	private String getMsg(String code, Object... args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}

	public void setPruefungDAO(PruefungDAO pruefungDAO) {
		this.pruefungDAO = pruefungDAO;
	}

	public void setPruefungsfachDAO(PruefungsfachDAO pruefungsfachDAO) {
		this.pruefungsfachDAO = pruefungsfachDAO;
	}

	public void setPruefungsleistungDAO(PruefungsleistungDAO pruefungsleistungDAO) {
		this.pruefungsleistungDAO = pruefungsleistungDAO;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setErgaenzungspruefungStrategie(ErgaenzungspruefungStrategie ergaenzungspruefungStrategie) {
		this.ergaenzungspruefungStrategie = ergaenzungspruefungStrategie;
	}

	public void setPruefungsleistungStrategie(PruefungsleistungStrategie pruefungsleistungStrategie) {
		this.pruefungsleistungStrategie = pruefungsleistungStrategie;
	}

	private static final String NICHT_EDITIERBAR = "pruefung.nichtEditierbar";

	private static final String KEINE_ERGAENZUNGSPRUEFUNG = "pruefung.keineErgaenzung";

}
