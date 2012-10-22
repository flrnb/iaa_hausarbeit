package de.nak.iaa.server.business.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;

import com.google.common.base.Optional;

import de.nak.iaa.server.business.IllegalPruefungsleistungException;
import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.dao.DAOMockBuilder;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * JUnit-Test für Implementierung von {@link PruefungService}
 * 
 * @author flrnb
 */
public class PruefungServiceImplTest {

	private PruefungServiceImpl service;

	private StudentServiceImpl studentService;

	private Pruefungsfach fach1;

	private Pruefungsfach fach2;

	private Pruefungsfach fach3;

	private Manipel man1;

	private Manipel man2;

	private Manipel man3;

	private Student student1;

	private Student student2;

	private Student student3;

	private Student studentAnderesManipel;

	private static final Date TODAY = new Date();

	private static Date TOMORROW;

	private static Date THE_DAY_AFTER_TOMORROW;

	private static MessageSource msgSource;

	@BeforeClass
	public static void setUpBeforeClass() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TODAY);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		TOMORROW = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		THE_DAY_AFTER_TOMORROW = cal.getTime();

		msgSource = EasyMock.createNiceMock(MessageSource.class);
		EasyMock.replay(msgSource);
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
		student1 = new Student(1, man1, "Bräunlich", "Ronny");
		student2 = new Student(2, man1, "Biel", "Christopher");
		student3 = new Student(3, man1, "Karagac", "Ibrahim");
		studentAnderesManipel = new Student(4, man2, "Anderes", "Manipel");

		studentService = new StudentServiceImpl();

		ManipelDAO manipelDAO = DAOMockBuilder.forClass(ManipelDAO.class).addEntities(man1, man2, man3).build();
		studentService.setManipelDAO(manipelDAO);

		StudentDAO studentDAO = DAOMockBuilder.forClass(StudentDAO.class)
				.addEntities(student1, student2, student3, studentAnderesManipel).build();
		studentService.setStudentDAO(studentDAO);

		service.setStudentService(studentService);

		PruefungsfachDAO pruefungsfachDAO = DAOMockBuilder.forClass(PruefungsfachDAO.class)
				.addEntities(fach1, fach2, fach3).build();
		service.setPruefungsfachDAO(pruefungsfachDAO);

		PruefungDAO pruefungDAO = DAOMockBuilder.forClass(PruefungDAO.class).build();
		service.setPruefungDAO(pruefungDAO);

		PruefungsleistungDAO pruefungsleistungDAO = DAOMockBuilder.forClass(PruefungsleistungDAO.class).build();
		service.setPruefungsleistungDAO(pruefungsleistungDAO);

		service.setMessageSource(msgSource);
	}

	@Test
	public void testGetAllPruefungsfaecher() {
		assertThat(service.getAllPruefungsfaecher(man1), hasItems(fach1, fach2));
		assertThat(service.getAllPruefungsfaecher(man2), hasItems(fach3));
		assertThat(service.getAllPruefungsfaecher(man3).size(), is(0));
	}

	@Test
	public void testGetPruefungsfachById() {
		assertThat(service.getPruefungsfachById(fach2.getId()).get(), is(equalTo(fach2)));
	}

	public void testGetPruefungsfachByIdNotPresent() {
		assertFalse(service.getPruefungsfachById(1000L).isPresent());
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
		assertThat(service.getPruefungById(pruefung.getId()).get(), is(equalTo(pruefung)));
	}

	public void testGetPruefungByIdNotPresent() {
		assertFalse(service.getPruefungById(1000L).isPresent());
	}

	@Test
	public void testGetAllPruefungsleistungen() throws IllegalPruefungsleistungException {
		Pruefungsleistung leistung1 = service.addPruefungsleistung(new Pruefung(TODAY, fach1), student1, Note.Sechs);
		Pruefungsleistung leistung2 = service.addPruefungsleistung(new Pruefung(TOMORROW, fach1), student1, Note.Fuenf);
		Pruefungsleistung leistung3 = service.addPruefungsleistung(new Pruefung(THE_DAY_AFTER_TOMORROW, fach1),
				student1, Note.Fuenf);
		Pruefungsleistung leistung4 = service.addPruefungsleistung(new Pruefung(TOMORROW, fach2), student1, Note.Fuenf);
		List<Pruefungsleistung> leistungen = service.getAllPruefungsleistungen(fach1, student1);
		assertThat(leistungen, hasItems(leistung1, leistung2, leistung3));
		assertThat(leistungen, not(hasItems(leistung4)));
	}

	@Test
	public void testIsPruefungsleistungEditable() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		assertTrue(service.isPruefungsleistungEditable(leistung.getId()));
	}

	@Test
	public void testIsPruefungsleistungNotEditable() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung1 = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);

		Pruefung pruefung2 = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung2, student1, Note.Fuenf);

		assertFalse(service.isPruefungsleistungEditable(leistung1.getId()));
		assertTrue(service.isPruefungsleistungEditable(leistung2.getId()));
	}

	@Test
	public void testUpdatePruefungsleistung() throws IllegalPruefungsleistungException {
		Pruefung pruefung = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung, student1, Note.EinsDrei);
		List<Pruefungsleistung> allPruefungsleistungen = service.getAllPruefungsleistungen(fach1, student1);
		assertThat(allPruefungsleistungen.size(), is(1));
		Pruefungsleistung leistung = allPruefungsleistungen.get(0);
		service.updatePruefungsleistung(leistung.getId(), Note.EinsSieben);
		assertThat(leistung.getNote(), is(Note.EinsSieben));
	}

	@Test(expected = IllegalStateException.class)
	public void testUpdatePruefungsleistungNotEditableIllegalState() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		Pruefung pruefung2 = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung2, student1, Note.ZweiDrei);
		service.updatePruefungsleistung(leistung.getId(), Note.Vier);
	}

	@Test
	public void testAddPruefungsleistung() throws IllegalPruefungsleistungException {
		Pruefung pruefung = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung, student1, Note.Drei);
		assertThat(leistung, is(equalTo(service.getAllPruefungsleistungen(fach1, student1).get(0))));
		assertThat(leistung.getVersuch(), is(equalTo(Versuch.Eins)));
	}

	@Test
	public void testAddPruefungsleistungZweiterVersuch() throws IllegalPruefungsleistungException {
		Pruefung pruefung = new Pruefung(TODAY, fach1);
		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung, student1, Note.Sechs);
		assertThat(leistung.getVersuch(), is(equalTo(Versuch.Eins)));
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung, student1, Note.Fuenf);
		assertThat(leistung2.getVersuch(), is(equalTo(Versuch.Zwei)));
	}

	@Test(expected = IllegalPruefungsleistungException.class)
	public void testAddPruefungsleistungNichtZulaessigBestanden() throws IllegalPruefungsleistungException {
		Pruefung pruefung = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung, student1, Note.Drei);
		service.addPruefungsleistung(pruefung, student1, Note.Vier);
	}

	@Test(expected = IllegalPruefungsleistungException.class)
	public void testAddPruefungsleistungNichtZulaessigDreiVersuche() throws IllegalPruefungsleistungException {
		Pruefung pruefung = new Pruefung(TODAY, fach1);
		service.addPruefungsleistung(pruefung, student1, Note.Sechs);
		service.addPruefungsleistung(pruefung, student1, Note.Sechs);
		service.addPruefungsleistung(pruefung, student1, Note.Sechs);
		service.addPruefungsleistung(pruefung, student1, Note.Vier);
	}

	@Test
	public void testErgaenzungsPruefung() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		Pruefung pruefung2 = new Pruefung(THE_DAY_AFTER_TOMORROW, fach1);
		Pruefung pruefung3 = new Pruefung(THE_DAY_AFTER_TOMORROW, fach2);

		Pruefungsleistung leistung1 = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung1, student2, Note.Fuenf);
		Pruefungsleistung leistung3 = service.addPruefungsleistung(pruefung2, student2, Note.Fuenf);
		Pruefungsleistung leistung4 = service.addPruefungsleistung(pruefung3, student3, Note.Fuenf);

		Map<Student, Date> studenten = service.getAllErgaenzungsPruefungsStudenten(man1, fach1);

		assertThat(studenten.keySet(), hasItems(student1, student2));
		assertThat(studenten.get(student1), is(equalTo(TODAY)));
		assertThat(studenten.get(student2), is(equalTo(THE_DAY_AFTER_TOMORROW)));

		assertTrue(service.isErgaenzungsPruefungZulaessig(leistung1));
		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung2));
		assertTrue(service.isErgaenzungsPruefungZulaessig(leistung3));
		assertTrue(service.isErgaenzungsPruefungZulaessig(leistung4));
	}

	@Test
	public void testErgaenzungsPruefungSchonVorhandenBestanden() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);

		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);

		assertTrue(service.isErgaenzungsPruefungZulaessig(leistung));

		service.addErgaenzungsPruefung(student1, fach1, TOMORROW, 80);

		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung));
		assertThat(service.getAllErgaenzungsPruefungsStudenten(man1, fach1).size(), is(0));
	}

	@Test
	public void testErgaenzungsPruefungSchonVorhandenNichtBestanden() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);

		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);

		assertTrue(service.isErgaenzungsPruefungZulaessig(leistung));

		service.addErgaenzungsPruefung(student1, fach1, TOMORROW, 60);

		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung));
		assertThat(service.getAllErgaenzungsPruefungsStudenten(man1, fach1).size(), is(0));
	}

	@Test
	public void testErgaenzungsPruefungSowiesoBestanden() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);

		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Drei);

		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung));
		assertThat(service.getAllErgaenzungsPruefungsStudenten(man1, fach1).size(), is(0));
	}

	@Test
	public void testErgaenzungsPruefungZweiterVersuchBestanden() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);

		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		assertTrue(service.isErgaenzungsPruefungZulaessig(leistung));
		service.addErgaenzungsPruefung(student1, fach1, THE_DAY_AFTER_TOMORROW, 50);

		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung));
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung1, student1, Note.Drei);

		assertThat(service.getAllErgaenzungsPruefungsStudenten(man1, fach1).size(), is(0));
		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung2));
	}

	public void testGetAllStudentenForPruefung() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);
		Pruefung pruefung2 = new Pruefung(TOMORROW, fach1);
		Pruefung pruefung3 = new Pruefung(TOMORROW, fach3);

		Pruefungsleistung leistung1 = service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung1, student2, Note.Drei);
		service.addPruefungsleistung(pruefung3, studentAnderesManipel, Note.Fuenf);

		Map<Student, Optional<Pruefungsleistung>> allStudentenForPruefung = service
				.getAllStudentenForPruefung(pruefung2);

		assertThat(allStudentenForPruefung.size(), is(3));
		assertThat(allStudentenForPruefung.keySet(), hasItems(student1, student2, student3));
		assertThat(allStudentenForPruefung.keySet(), not(hasItems(studentAnderesManipel)));
		assertThat(allStudentenForPruefung.get(student1).get(), is(equalTo(leistung1)));
		assertThat(allStudentenForPruefung.get(student2).get(), is(equalTo(leistung2)));
		assertFalse(allStudentenForPruefung.get(student3).isPresent());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddErgaenzungspruefungNichtZulaessig() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = new Pruefung(TODAY, fach1);

		Pruefungsleistung leistung = service.addPruefungsleistung(pruefung1, student1, Note.Drei);

		assertFalse(service.isErgaenzungsPruefungZulaessig(leistung));
		service.addErgaenzungsPruefung(student1, fach1, TODAY, 80);
	}

	@Test
	public void testGetAktuelleNoteVorhanden() throws IllegalPruefungsleistungException {
		Pruefung pruefung = service.addPruefung(fach1, TODAY);
		service.addPruefungsleistung(pruefung, student1, Note.EinsSieben);
		assertThat(service.getAktuelleNote(student1, fach1).get(), is(equalTo(Note.EinsSieben)));
	}

	@Test
	public void testGetAktuelleNoteZweiVersuche() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = service.addPruefung(fach1, TODAY);
		service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		Pruefung pruefung2 = service.addPruefung(fach1, TOMORROW);
		service.addPruefungsleistung(pruefung2, student1, Note.Drei);
		assertThat(service.getAktuelleNote(student1, fach1).get(), is(equalTo(Note.Drei)));
	}

	@Test
	public void testGetAktuelleNoteDreiVersuche() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = service.addPruefung(fach1, TODAY);
		service.addPruefungsleistung(pruefung1, student1, Note.Fuenf);
		Pruefung pruefung2 = service.addPruefung(fach1, TOMORROW);
		service.addPruefungsleistung(pruefung2, student1, Note.Sechs);
		Pruefung pruefung3 = service.addPruefung(fach1, TOMORROW);
		service.addPruefungsleistung(pruefung3, student1, Note.Zwei);

	}

	@Test
	public void testGetAktuelleNoteErgaenzung() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = service.addPruefung(fach1, TODAY);
		service.addPruefungsleistung(pruefung1, student1, Note.Sechs);
		Pruefung pruefung2 = service.addPruefung(fach1, TOMORROW);
		service.addPruefungsleistung(pruefung2, student1, Note.Fuenf);
		service.addErgaenzungsPruefung(student1, fach1, THE_DAY_AFTER_TOMORROW, 90);
		assertThat(service.getAktuelleNote(student1, fach1).get(), is(equalTo(Note.Vier)));
	}

	@Test
	public void testGetAktuelleNoteErgaenzungDurchgefallen() throws IllegalPruefungsleistungException {
		Pruefung pruefung1 = service.addPruefung(fach1, TODAY);
		service.addPruefungsleistung(pruefung1, student1, Note.Sechs);
		Pruefung pruefung2 = service.addPruefung(fach1, TOMORROW);
		Pruefungsleistung leistung2 = service.addPruefungsleistung(pruefung2, student1, Note.Fuenf);
		service.addErgaenzungsPruefung(student1, fach1, THE_DAY_AFTER_TOMORROW, 40);
		assertThat(service.getAktuelleNote(student1, fach1).get(), is(equalTo(Note.Fuenf)));
	}
}
