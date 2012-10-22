package de.nak.iaa.server.business.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
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
		// pruefungsfachDAO.findByManipel(manipel);
		return ImmutableList.copyOf(Iterables.filter(pruefungsfachDAO.findAll(), new Predicate<Pruefungsfach>() {
			@Override
			public boolean apply(Pruefungsfach fach) {
				return manipel.equals(fach.getManipel());
			}
		}));
	}

	@Override
	public void updatePruefungsleistung(Long id, Note note) {
		pruefungsleistungDAO.findById(id, false).setNote(note);
	}

	@Override
	public Pruefungsleistung addPruefungsleistung(Pruefung pruefung, Student student, Note note) {
		// FIXME Datum?
		Pruefungsleistung leistung = new Pruefungsleistung(Versuch.Eins, null, pruefung, note);
		return pruefungsleistungDAO.makePersistent(leistung);
	}

	@Override
	public List<Pruefungsleistung> getAllPruefungsleistungen(final Pruefungsfach fach, final Student student) {
		return ImmutableList.copyOf(Iterables.filter(pruefungsleistungDAO.findAll(),
				new Predicate<Pruefungsleistung>() {
					@Override
					public boolean apply(Pruefungsleistung leistung) {
						return fach.equals(leistung.getPruefung().getPruefungsfach())
						// && student.equals(leistung.getStudent())
						;
					}
				}));
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
	public Pruefung addPruefung(Pruefungsfach fach, Date datum) {
		Pruefung pruefung = new Pruefung(datum, fach);
		return pruefungDAO.makePersistent(pruefung);
	}

	@Override
	public boolean isPruefungsleistungEditable(Long id) {
		Pruefungsleistung leistung = pruefungsleistungDAO.findById(id, false);
		if (leistung == null)
			throw new IllegalArgumentException();
		return true;
	}

	@Override
	public Map<Student, Optional<Pruefungsleistung>> getAllStudentenForPruefung(Pruefung pruefung) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Note> getAktuelleNote(Student student, Pruefungsfach fach) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Student, Date> getAllErgaenzungsPruefungsStudenten(Manipel manipel, Pruefungsfach fach) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isErgaenzungsPruefungZulaessig(Pruefungsleistung pruefungsleistung) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ErgaenzungsPruefung addErgaenzungsPruefung(Pruefungsleistung pruefungsleistung, int prozent) {
		// TODO Auto-generated method stub
		return null;
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

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
