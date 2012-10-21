package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;

public class PruefungPersistenceTest extends ApplicationContextAwareTest {

	@Resource
	private PruefungDAO pruefungDAO;
	@Resource
	private PruefungsfachDAO pruefungsfachDAO;
	@Resource
	private ManipelDAO manipelDAO;

	@Test
	public void testPersist() {
		Manipel manipel = manipelDAO.findAll().get(0);
		Pruefungsfach pruefungsfach = pruefungsfachDAO
				.makePersistent(new Pruefungsfach("Fach", "Beschreibung",
						manipel));
		int countBefore = pruefungDAO.findAll().size();
		Pruefung pruefung = new Pruefung(new Date(), pruefungsfach);
		pruefungDAO.makePersistent(pruefung);
		int countAfter = pruefungDAO.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
	}
}
