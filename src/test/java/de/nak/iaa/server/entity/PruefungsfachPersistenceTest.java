package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;

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
		// einfach mal Manipel Nr. 1 nehmen
		testManipel = manipelDAO.findById(Long.valueOf(1), false);
	}

	@Test
	public void testPersistPruefungsfach() {
		int countBefore = pruefungsfachDAO.findAll().size();
		Pruefungsfach fach = new Pruefungsfach(TITEL, BESCHREIBUNG, testManipel);
		pruefungsfachDAO.makePersistent(fach);

		int countAfter = pruefungsfachDAO.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
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
