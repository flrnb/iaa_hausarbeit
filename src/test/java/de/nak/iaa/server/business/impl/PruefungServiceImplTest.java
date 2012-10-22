package de.nak.iaa.server.business.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.dao.DAOMockBuilder;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;

/**
 * JUnit-Test für Implementierung von {@link PruefungService}
 * 
 * @author flrnb
 */
public class PruefungServiceImplTest {

	private PruefungServiceImpl service;

	private Pruefungsfach fach1;

	private Pruefungsfach fach2;

	private Pruefungsfach fach3;

	private Manipel man1;

	private Manipel man2;

	private Manipel man3;

	private Student student;

	private static final Date TODAY = new Date();

	private static Date TOMORROW;

	public static void setUpBeforeClass() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TODAY);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		TOMORROW = cal.getTime();
	}

	@Before
	public void setUp() {
		service = new PruefungServiceImpl();
		man1 = new Manipel(2009, Studienrichtung.WInf);
		man2 = new Manipel(2010, Studienrichtung.WInf);
		man3 = new Manipel(2010, Studienrichtung.WIng);
		fach1 = new Pruefungsfach("Fach1", "Beschreibung1", man1);
		fach2 = new Pruefungsfach("Fach2", "Beschreibung2", man1);
		fach3 = new Pruefungsfach("Fach3", "Beschreibung3", man2);
		student = new Student();
		PruefungsfachDAO pruefungsfachDAO = DAOMockBuilder.forClass(PruefungsfachDAO.class)
				.addEntities(fach1, fach2, fach3).build();
		service.setPruefungsfachDAO(pruefungsfachDAO);
	}

	@Test
	public void testGetAllPruefungsfaecher() {
		assertThat(service.getAllPruefungsfaecher(man1), hasItems(fach1, fach2));
		assertThat(service.getAllPruefungsfaecher(man2), hasItems(fach3));
		assertThat(service.getAllPruefungsfaecher(man3).size(), is(0));
	}

	@Test
	public void testIsPruefungsleistungEditable() {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student, Note.Fuenf);
		assertTrue(service.isPruefungsleistungEditable(leistung.getId()));
	}

	@Test
	public void testIsPruefungsleistungNotEditable() {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung1, student, Note.Fuenf);
		Pruefungsleistung leistung1 = service.getAllPruefungsleistungen(fach1, student).get(0);
		Pruefung pruefung2 = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung2, student, Note.Fuenf);
		Pruefungsleistung leistung2 = service.getAllPruefungsleistungen(fach1, student).get(0);
		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
		assertTrue(service.isPruefungsleistungEditable(leistung2.getId()));
	}

	@Test
	public void testUpdatePruefungsleistung() {
		Pruefung pruefung = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung, student, Note.EinsDrei);
		List<Pruefungsleistung> allPruefungsleistungen = service.getAllPruefungsleistungen(fach1, student);
		assertThat(allPruefungsleistungen.size(), is(1));
		Pruefungsleistung leistung = allPruefungsleistungen.get(0);
		service.updatePruefungsleistung(leistung.getId(), Note.EinsSieben);
		assertThat(leistung.getNote(), is(Note.EinsSieben));
	}

	@Test(expected = IllegalStateException.class)
	public void testUpdatePruefungsleistungNotEditableIllegalState() {
		Pruefung pruefung1 = new Pruefung();
		service.addPruefungsleistung(pruefung1, student, Note.Fuenf);
		Pruefungsleistung leistung1 = service.getAllPruefungsleistungen(fach1, student).get(0);
		Pruefung pruefung2 = new Pruefung();
		service.addPruefungsleistung(pruefung2, student, Note.ZweiDrei);
		service.updatePruefungsleistung(leistung1.getId(), Note.Vier);
	}

	@Test
	public void testGetPruefungsfachById() {
		assertThat(service.getPruefungsfachById(fach2.getId()), is(equalTo(fach2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPruefungsfachByIdNotPresent() {
		service.getPruefungsfachById(1000L);
	}

	@Test
	public void testGetAllPruefungen() {
		assertThat(service.getAllPruefungen(fach1).size(), is(0));
		service.addPruefung(fach1, TODAY);
		service.addPruefung(fach1, TOMORROW);
		service.addPruefung(fach2, TODAY);
		service.getAllPruefungen(fach1);
		assertThat(service.getAllPruefungen(fach1).size(), is(2));
	}

	@Test
	public void testGetPruefungById() {
		service.addPruefung(fach1, TODAY);
		Pruefung pruefung = service.getAllPruefungen(fach1).get(0);
		assertThat(service.getPruefungById(pruefung.getId()), is(equalTo(pruefung)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPruefungByIdNotPresent() {
		service.getPruefungById(1000L);
	}

	@Test
	public void testGetAllPruefungsleistungen() {
		// service.addPruefungsleistung(pruefung, student, note)
		service.getAllPruefungsleistungen(fach1, student);
	}

}
