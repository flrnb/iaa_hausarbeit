package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;

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
		testManipel = new Manipel(2007, Studienrichtung.WInf);
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

	@Test(expected = DataIntegrityViolationException.class)
	public void testUniqueContraints() {
		Pruefungsfach fach = new Pruefungsfach(TITEL, BESCHREIBUNG, testManipel);
		Pruefungsfach fach2 = new Pruefungsfach(TITEL,
				"Beschreibung ist irrelevant", testManipel);
		pruefungsfachDAO.makePersistent(fach);
		pruefungsfachDAO.makePersistent(fach2);
	}

	@Test
	public void findByManipelTest() {
		Manipel einManipel = new Manipel(2005, Studienrichtung.BWL);
		Manipel anderesManipel = new Manipel(2006, Studienrichtung.WIng);
		manipelDAO.makePersistent(einManipel);
		manipelDAO.makePersistent(anderesManipel);
		Pruefungsfach p1 = new Pruefungsfach(TITEL, BESCHREIBUNG, einManipel);
		Pruefungsfach p2 = new Pruefungsfach("Anderer Titel",
				"Andere Beschreibung", einManipel);
		Pruefungsfach p3 = new Pruefungsfach("Anderes Manipel", "Beschreibung",
				anderesManipel);

		pruefungsfachDAO.makePersistent(p1);
		pruefungsfachDAO.makePersistent(p2);
		pruefungsfachDAO.makePersistent(p3);

		List<Pruefungsfach> list = pruefungsfachDAO
				.findePruefungsfaecherFuerManipel(einManipel);
		assertThat(list.size(), is(equalTo(2)));
		assertThat(list.contains(p3), is(Boolean.FALSE));
	}

}
