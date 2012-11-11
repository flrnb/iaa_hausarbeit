package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;

import de.nak.iaa.TransactionalApplicationContextAwareTest;
import de.nak.iaa.server.dao.DozentDAO;

/**
 * 
 * @author Ibrahim Karagac
 * 
 */
public class DozentPersistenceTest extends
		TransactionalApplicationContextAwareTest {

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
