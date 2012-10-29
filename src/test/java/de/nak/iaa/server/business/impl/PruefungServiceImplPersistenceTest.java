package de.nak.iaa.server.business.impl;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.PruefungsleistungenUpdateException.IllegalPruefungsleistungException;
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
	public void testIsPruefungsleistungEditableNachfolgerGeloescht() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = service.addPruefung(fach, datum, dozent);
		Pruefungsleistung leistung1 = service.addPruefungsleistung(pruefung1, student, Note.Sechs);
		assertTrue(service.isPruefungsleistungEditable(leistung1.getId()));

		Pruefung pruefung2 = service.addPruefung(fach, datum, dozent);
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung2, student, Note.DreiDrei);
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
		assertTrue(service.isPruefungsleistungEditable(leistung2.getId()));

		service.stornierePruefungsleistung(leistung2.getId());
		// das löschen einer zukünftigen Prüfungsleistung macht die Vorgänger
		// nicht editierbar
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
	}

}
