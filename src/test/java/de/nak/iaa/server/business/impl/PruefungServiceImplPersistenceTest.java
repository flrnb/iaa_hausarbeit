package de.nak.iaa.server.business.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.javatuples.Triplet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.business.IllegalUpdateException;
import de.nak.iaa.server.business.IllegalUpdateException.IllegalPruefungsleistungException;
import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.PruefungsleistungAenderung.Delete;
import de.nak.iaa.server.business.PruefungsleistungAenderung.Update;
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
import de.nak.iaa.server.fachwert.Versuch;

/**
 * PersistenceTest für PruefungService-Implementierung. Einige Funktionalitäten
 * des Persistence-Layers waren nicht sinnvoll zu mocken.
 * 
 * @author flrnb
 */
public class PruefungServiceImplPersistenceTest extends ApplicationContextAwareTest {

	@Autowired
	public PruefungService service;

	@Autowired
	public ManipelDAO manipelDAO;

	@Autowired
	public DozentDAO dozentDAO;

	@Autowired
	public StudentDAO studentDAO;

	@Autowired
	public PruefungsfachDAO pruefungsfachDAO;

	private Pruefungsfach fach;

	private Date datum1;

	private Date datum2;

	private Date datum3;

	private Dozent dozent;

	private Manipel manipel;

	private Student student;

	private Pruefung pruefung1;
	private Pruefung pruefung2;
	private Pruefung pruefung3;

	@Before
	public void setUp() {

		manipel = new Manipel(2007, Studienrichtung.WInf);
		manipelDAO.makePersistent(manipel);
		fach = new Pruefungsfach("Fach1", "Fach1 Beschreibung", manipel);
		pruefungsfachDAO.makePersistent(fach);
		dozent = new Dozent("Mustermann", "Max");
		dozentDAO.makePersistent(dozent);
		datum1 = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(datum1);
		cal.add(10, Calendar.DAY_OF_YEAR);
		datum2 = cal.getTime();
		cal.add(10, Calendar.DAY_OF_YEAR);
		datum3 = cal.getTime();

		student = new Student(1234, manipel, "Gefallen", "Durch");
		studentDAO.makePersistent(student);

		pruefung1 = service.addPruefung(fach, datum1, dozent);
		pruefung2 = service.addPruefung(fach, datum2, dozent);
		pruefung3 = service.addPruefung(fach, datum3, dozent);
	}

	@Test
	public void testIsPruefungsleistungNotEditable() throws IllegalUpdateException {
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();
		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);
		Pruefungsleistung leistung1 = service.getAllPruefungsleistungen(fach, student).get(0);

		leistungen.clear();
		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung2, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);

		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
	}

	@Test(expected = IllegalUpdateException.class)
	public void testUpdatePruefungsleistungNotEditableIllegalState() throws IllegalUpdateException,
			IllegalPruefungsleistungException {

		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();
		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Fuenf));

		service.addPruefungsleistungen(leistungen);
		Pruefungsleistung leistung = service.getAllPruefungsleistungen(fach, student).get(0);

		leistungen.clear();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung2, student, Note.ZweiDrei));

		service.addPruefungsleistungen(leistungen);

		service.updatePruefungsleistungen(Arrays.asList(new Update(leistung.getId(), Note.Vier)));
	}

	@Test
	public void testIsPruefungsleistungEditableNachfolgerGeloescht() throws IllegalPruefungsleistungException,
			IllegalUpdateException {
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();
		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Sechs));
		service.addPruefungsleistungen(leistungen);
		Pruefungsleistung leistung1 = service.getAllPruefungsleistungen(fach, student).get(0);
		assertTrue(service.isPruefungsleistungEditable(leistung1.getId()));

		leistungen.clear();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung2, student, Note.DreiDrei));
		service.addPruefungsleistungen(leistungen);
		Pruefungsleistung leistung2 = service.getAllPruefungsleistungen(fach, student).get(1);
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
		assertTrue(service.isPruefungsleistungEditable(leistung2.getId()));

		service.updatePruefungsleistungen(Arrays.asList(new Delete(leistung2.getId())));
		// das löschen einer zukünftigen Prüfungsleistung macht die Vorgänger
		// nicht editierbar

		assertThat(service.getAllPruefungsleistungen(fach, student).size(), is(1));
		assertThat(service.getAllPruefungsleistungen(fach, student), hasItems(leistung1));
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
	}

	@Test
	public void testGetPruefungsleistungHistorieEmpty() {
		Map<Versuch, SortedMap<Date, Pruefungsleistung>> pruefungsleistungHistorie = service
				.getPruefungsleistungHistorie(student, fach);
		assertTrue(pruefungsleistungHistorie.get(Versuch.Eins).isEmpty());
		assertTrue(pruefungsleistungHistorie.get(Versuch.Zwei).isEmpty());
		assertTrue(pruefungsleistungHistorie.get(Versuch.Drei).isEmpty());
	}

	@Test
	public void testGetPruefungsleistungHistorie() throws IllegalUpdateException {
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);
		leistungen.clear();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung2, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);
		leistungen.clear();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung3, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);

		Map<Versuch, SortedMap<Date, Pruefungsleistung>> pruefungsleistungHistorie = service
				.getPruefungsleistungHistorie(student, fach);
		assertThat(pruefungsleistungHistorie.get(Versuch.Eins).size(), is(1));
		assertThat(pruefungsleistungHistorie.get(Versuch.Zwei).size(), is(1));
		assertThat(pruefungsleistungHistorie.get(Versuch.Drei).size(), is(1));
	}

	@Test
	public void testGetPruefungsleistungHistorieUpdate() throws IllegalUpdateException {
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);
		leistungen.clear();

		Pruefungsleistung leistung = service.getAllPruefungsleistungen(fach, student).get(0);

		service.updatePruefungsleistungen(Arrays.asList(new Update(leistung.getId(), Note.Vier)));

		Map<Versuch, SortedMap<Date, Pruefungsleistung>> pruefungsleistungHistorie = service
				.getPruefungsleistungHistorie(student, fach);
		assertThat(pruefungsleistungHistorie.get(Versuch.Eins).size(), is(2));
		assertThat(pruefungsleistungHistorie.get(Versuch.Zwei).size(), is(0));
		assertThat(pruefungsleistungHistorie.get(Versuch.Drei).size(), is(0));
	}

	@Test
	public void testGetPruefungsleistungHistorieGeloescht() throws IllegalUpdateException {
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();

		leistungen.add(new Triplet<Pruefung, Student, Note>(pruefung1, student, Note.Fuenf));
		service.addPruefungsleistungen(leistungen);
		leistungen.clear();

		Pruefungsleistung leistung = service.getAllPruefungsleistungen(fach, student).get(0);

		service.updatePruefungsleistungen(Arrays.asList(new Delete(leistung.getId())));

		Map<Versuch, SortedMap<Date, Pruefungsleistung>> pruefungsleistungHistorie = service
				.getPruefungsleistungHistorie(student, fach);
		assertThat(pruefungsleistungHistorie.get(Versuch.Eins).size(), is(2));
		assertThat(pruefungsleistungHistorie.get(Versuch.Zwei).size(), is(0));
		assertThat(pruefungsleistungHistorie.get(Versuch.Drei).size(), is(0));
	}

}
