package de.nak.iaa.server.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.PruefungsleistungenUpdateException;
import de.nak.iaa.server.business.PruefungsleistungenUpdateException.IllegalPruefungsleistungException;
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
 * @author flrnb
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

	@Override
	public List<Pruefungsfach> getAllPruefungsfaecher(final Manipel manipel) {
		return pruefungsfachDAO.findByManipel(manipel);
	}

	@Override
	public void updatePruefungsleistung(Long id, Note note) {
		if (!isPruefungsleistungEditable(id))
			throw new IllegalStateException(getMsg(NICHT_EDITIERBAR));
		Pruefungsleistung leistung = pruefungsleistungDAO.findById(id, false);
		leistung.setNote(note);
		pruefungsleistungDAO.makePersistent(leistung);
	}

	@Override
	public void stornierePruefungsleistung(Long id) {
		if (!isPruefungsleistungEditable(id))
			throw new IllegalStateException(getMsg(NICHT_EDITIERBAR));
		Pruefungsleistung toDelete = pruefungsleistungDAO.findById(id, false);
		pruefungsleistungDAO.makeTransient(toDelete);
	}

	@Override
	public Pruefungsleistung addPruefungsleistung(Pruefung pruefung, Student student, Note note)
			throws IllegalPruefungsleistungException {
		Optional<Pruefungsleistung> leistung = getLetzterVersuch(pruefung.getPruefungsfach(), student);
		Versuch nextVersuch = null;
		if (leistung.isPresent()) {
			Versuch versuch = leistung.get().getVersuch();
			if (isBestanden(leistung.get()) || !versuch.next().isPresent())
				throw new IllegalPruefungsleistungException(student, getMsg(ZU_OFT));
			nextVersuch = versuch.next().get();
		} else {
			nextVersuch = Versuch.Eins;
		}
		Pruefungsleistung neueLeistung = new Pruefungsleistung(nextVersuch, pruefung, note, student);
		return pruefungsleistungDAO.makePersistent(neueLeistung);
	}

	@Override
	public void addPruefungsleistungen(List<Triplet<Pruefung, Student, Note>> leistungen)
			throws PruefungsleistungenUpdateException {
		PruefungsleistungenUpdateException exc = new PruefungsleistungenUpdateException();
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

	/**
	 * FIXME Wenn es in der fachlichen Zukunft eine gelöschte Prüfungsleistung
	 * gibt, sollten die Vorgänger nicht editierbar sein
	 */
	@Override
	public boolean isPruefungsleistungEditable(Long id) {
		Pruefungsleistung leistung = pruefungsleistungDAO.findById(id, false);
		if (leistung == null)
			throw new IllegalArgumentException();
		List<Pruefungsleistung> leistungen = getAllPruefungsleistungen(leistung.getPruefung().getPruefungsfach(),
				leistung.getStudent());
		for (Pruefungsleistung eachLeistung : leistungen)
			if (eachLeistung.getVersuch().toInt() > leistung.getVersuch().toInt())
				return false;
		if (leistung.getErgaenzungsPruefung() != null)
			return false;
		if (hatFachlichenNachfolger(leistung))
			return false;
		return true;
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
				ErgaenzungsPruefung ergPruefung = leistung.getErgaenzungsPruefung();
				if (ergPruefung == null)
					return leistung.getNote();
				else
					return ergPruefung.getNote();
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
					if (isErgaenzungsPruefungZulaessig(leistung))
						result.put(student, leistung.getPruefung().getDatum());
					return leistung;
				}
			});
		}
		return result;
	}

	@Override
	public boolean isErgaenzungsPruefungZulaessig(final Pruefungsleistung pruefungsleistung) {
		Pruefungsfach fach = pruefungsleistung.getPruefung().getPruefungsfach();
		Student student = pruefungsleistung.getStudent();
		Boolean isLetzterVersuch = getLetzterVersuch(fach, student).transform(
				new Function<Pruefungsleistung, Boolean>() {
					@Override
					public Boolean apply(Pruefungsleistung leistung) {
						return leistung.equals(pruefungsleistung);
					}
				}).or(false);
		return isLetzterVersuch && pruefungsleistung.getNote().equals(Note.Fuenf)
				&& pruefungsleistung.getErgaenzungsPruefung() == null;
	}

	@Override
	public ErgaenzungsPruefung addErgaenzungsPruefung(Student student, Pruefungsfach fach, Date datum, int prozent) {
		Optional<Pruefungsleistung> letzterVersuch = getLetzterVersuch(fach, student);
		if (!letzterVersuch.isPresent() || !isErgaenzungsPruefungZulaessig(letzterVersuch.get()))
			throw new IllegalStateException(KEINE_ERGAENZUNGSPRUEFUNG);
		Note note = (prozent >= 80) ? Note.Vier : Note.Fuenf;
		ErgaenzungsPruefung ergaenzungsPruefung = new ErgaenzungsPruefung(note, datum);
		letzterVersuch.get().setErgaenzungsPruefung(ergaenzungsPruefung);
		return ergaenzungsPruefung;
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
	private boolean hatFachlichenNachfolger(Pruefungsleistung leistung) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isBestanden(Pruefungsleistung leistung) {
		switch (leistung.getNote()) {
		case Sechs:
			return false;
		case Fuenf:
			ErgaenzungsPruefung ergaenzungsPruefung = leistung.getErgaenzungsPruefung();
			if (ergaenzungsPruefung == null)
				return false;
			return ergaenzungsPruefung.getNote().getNote() <= Note.Vier.getNote();
		default:
			return true;
		}
	}

	private String getMsg(String code) {
		return messageSource.getMessage(code, new Object[] {}, Locale.getDefault());
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

	private static final String ZU_OFT = "pruefung.zuOft";

	private static final String NICHT_EDITIERBAR = "pruefung.nichtEditierbar";

	private static final String KEINE_ERGAENZUNGSPRUEFUNG = "pruefung.keineErgaenzung";

}
