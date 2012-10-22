package de.nak.iaa.server.business.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;

/**
 * Implementierung von {@link PruefungService}
 * 
 * @author flrnb
 */
public class PruefungServiceImpl implements PruefungService {

	@Autowired
	private StudentService studentService;

	@Autowired
	private PruefungsfachDAO pruefungsfachDAO;

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

	public void setPruefungsfachDAO(PruefungsfachDAO pruefungsfachDAO) {
		this.pruefungsfachDAO = pruefungsfachDAO;
	}

	@Override
	public void updatePruefungsleistung(Long id, Note note) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPruefungsleistung(Pruefung pruefung, Date datum, Student student, Note note) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Pruefungsleistung> getAllPruefungsleistungen(Pruefungsfach fach, Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pruefungsfach getPruefungsfachById(Long id) {
		return pruefungsfachDAO.findById(id, false);
	}

	@Override
	public Pruefung getPruefungById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pruefung> getAllPruefungen(Pruefungsfach pruefungsfach) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPruefung(Pruefungsfach fach, Date datum) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPruefungsleistungEditable(Long id) {
		// TODO Auto-generated method stub
		return false;
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

}
