package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.DozentDAO;

public class DozentPersistenceTest extends ApplicationContextAwareTest {

	@Resource
	private DozentDAO dozentDAO;

	@Test
	public void testPersistDozent() {
		int countBefore = dozentDAO.findAll().size();
		Dozent dozent = new Dozent();
		dozentDAO.makePersistent(dozent);
		int countAfter = dozentDAO.findAll().size();
		assertThat(countAfter, is(equalTo(countBefore + 1)));

	}

}
