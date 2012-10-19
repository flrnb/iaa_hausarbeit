package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.fachwert.Studienrichtung;

public class PruefungsfachPersistenceTest extends ApplicationContextAwareTest {

	private static final String BESCHREIBUNG = "Die allerallerbeste Vorlesung überhaupt...";
	private static final String TITEL = "Internetanwendungsarchitektur";
	private Manipel testManipel;
	@Resource
	private ManipelDAO manipelDAO;
	@Resource
	private PruefungsfachDAO pruefungsfachDAO;

	@Before
	public void setUp() {
		int jahrgang = 2009;
		testManipel = new Manipel(jahrgang, Studienrichtung.WInf);
		testManipel = manipelDAO.makePersistent(testManipel);
	}

	@Test
	public void testPersistPruefungsfach() {
		int countBefore = pruefungsfachDAO.findAll().size();
		Pruefungsfach fach = new Pruefungsfach(TITEL, BESCHREIBUNG, testManipel);
		pruefungsfachDAO.makePersistent(fach);

		int countAfter = pruefungsfachDAO.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
	}

	@Test
	public void testUnterschiedlichePruefungSelbesManipel() {
		Pruefungsfach fach = new Pruefungsfach(TITEL, BESCHREIBUNG, testManipel);
		Pruefungsfach fach2 = new Pruefungsfach("Mathe",
				"Nicht so toll wie IAA", testManipel);
		pruefungsfachDAO.makePersistent(fach);
		pruefungsfachDAO.makePersistent(fach2);
		List<Pruefungsfach> faecher = pruefungsfachDAO.findAll();
		assertThat(faecher.get(0).getManipel(), is(equalTo(faecher.get(1)
				.getManipel())));
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testUniqueContraints() {
		Pruefungsfach fach = new Pruefungsfach(TITEL, BESCHREIBUNG, testManipel);
		Pruefungsfach fach2 = new Pruefungsfach(TITEL,
				"Beschreibung ist irrelevant", testManipel);
		pruefungsfachDAO.makePersistent(fach);
		pruefungsfachDAO.makePersistent(fach2);
	}

	@After
	public void tearDown() {
		manipelDAO.makeTransient(testManipel);
	}

}
