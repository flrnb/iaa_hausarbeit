package de.nak.iaa.server.business.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.javatuples.Triplet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.business.IllegalUpdateException;
import de.nak.iaa.server.business.IllegalUpdateException.IllegalPruefungsleistungException;
import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.PruefungsleistungAenderung.Delete;
import de.nak.iaa.server.dao.DozentDAO;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.entity.Dozent;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;

/**
 * PersistenceTest für PruefungService-Implementierung. Einige Funktionalitäten
 * des Persistence-Layers waren nicht sinnvoll zu mocken.
 * 
 * @author flrnb
 */
public class PruefungServiceImplPersistenceTest extends ApplicationContextAwareTest {

	@Autowired
	public PruefungService service;

	@Resource
	public ManipelDAO manipelDAO;

	@Resource
	public DozentDAO dozentDAO;

	@Resource
	public StudentDAO studentDAO;

	@Resource
	public PruefungsfachDAO pruefungsfachDAO;

	private Pruefungsfach fach;

	private Date datum;

	private Dozent dozent;

	private Manipel manipel;

	private Student student;

	@Before
	public void setUp() {
		manipel = new Manipel(2007, Studienrichtung.WInf);
		manipelDAO.makePersistent(manipel);
		fach = new Pruefungsfach("Fach1", "Fach1 Beschreibung", manipel);
		pruefungsfachDAO.makePersistent(fach);
		dozent = new Dozent("Mustermann", "Max");
		dozentDAO.makePersistent(dozent);
		datum = new Date();
		student = new Student(1234, manipel, "Gefallen", "Durch");
		studentDAO.makePersistent(student);
	}

	@Test
	public void testIsPruefungsleistungEditableNachfolgerGeloescht() throws IllegalPruefungsleistungException,
			IllegalUpdateException {
		Pruefung pruefung1 = service.addPruefung(fach, datum, dozent);
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();
		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Sechs));
		service.addPruefungsleistungen(leistungen);
		Pruefungsleistung leistung1 = service.getAllPruefungsleistungen(fach, student).get(0);
		assertTrue(service.isPruefungsleistungEditable(leistung1.getId()));

		Pruefung pruefung2 = service.addPruefung(fach, datum, dozent);
		leistungen.clear();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung2, student, Note.DreiDrei));
		service.addPruefungsleistungen(leistungen);
		Pruefungsleistung leistung2 = service.getAllPruefungsleistungen(fach, student).get(1);
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
		assertTrue(service.isPruefungsleistungEditable(leistung2.getId()));

		service.updatePruefungsleistungen(Arrays.asList(new Delete(leistung2.getId())));
		// das löschen einer zukünftigen Prüfungsleistung macht die Vorgänger
		// nicht editierbar
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
	}
}
