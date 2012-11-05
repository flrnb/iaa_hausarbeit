package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.TransactionalApplicationContextAwareTest;
import de.nak.iaa.server.dao.DozentDAO;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungPersistenceTest extends
		TransactionalApplicationContextAwareTest {

	@Resource
	private PruefungsleistungDAO pruefungsleistungDAO;
	@Resource
	private PruefungDAO pruefungDAO;
	@Resource
	private PruefungsfachDAO pruefungsfachDAO;
	@Resource
	private ManipelDAO manipelDAO;
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private DozentDAO dozentDAO;
	private Pruefungsfach pruefungsfach;
	private Pruefung pruefung;
	private Student student;
	private Dozent dozent;

	@Before
	public void setUp() {
		dozent = new Dozent("b", "a");
		dozent = dozentDAO.makePersistent(dozent);
		pruefungsfach = new Pruefungsfach("Titel", "beschreibung", manipelDAO
				.findAll().get(0));
		pruefungsfach = pruefungsfachDAO.makePersistent(pruefungsfach);
		pruefung = new Pruefung(new Date(), pruefungsfach, dozent);
		pruefung = pruefungDAO.makePersistent(pruefung);
		Manipel manipel = new Manipel(2007, Studienrichtung.BWL);
		manipel = manipelDAO.makePersistent(manipel);
		student = new Student(1, manipel, "Name", "Vorname");
		student = studentDAO.makePersistent(student);
	}

	@Test
	public void testPersistierung() {
		int countBefore = pruefungsleistungDAO.findAll().size();
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, pruefung,
				Note.Drei, student);
		pruefungsleistungDAO.makePersistent(pl);

		int countAfter = pruefungsleistungDAO.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
	}

	@Test
	public void testPersistierungMitErgaenzungspruefung() {
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, pruefung,
				Note.EinsDrei, student);
		pl.setErgaenzungsPruefung(new ErgaenzungsPruefung(Note.Drei, new Date()));
		pl = pruefungsleistungDAO.makePersistent(pl);
		Long id = pl.getId();
		pl = pruefungsleistungDAO.findById(id, false);
		assertThat(pl.getErgaenzungsPruefung().getId(), is(notNullValue()));
	}

}
