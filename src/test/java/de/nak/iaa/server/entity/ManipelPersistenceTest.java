package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import de.nak.iaa.TransactionalApplicationContextAwareTest;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.fachwert.Studienrichtung;

/**
 * 
 * @author Ronny Bräunlich
 * 
 */
public class ManipelPersistenceTest extends
		TransactionalApplicationContextAwareTest {

	private static final int jahrgang = 2007;
	private final Studienrichtung studienrichtung = Studienrichtung.WInf;

	@Resource
	private ManipelDAO manipelDAO;

	@Test
	public void testPersistStudienrichtung() {
		int countBefore = manipelDAO.findAll().size();
		Manipel manipel = new Manipel(jahrgang, studienrichtung);
		manipelDAO.makePersistent(manipel);

		int countAfter = manipelDAO.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
		manipel = manipelDAO.findByExample(manipel, new String[] {}).get(0);
		assertThat(manipel.getId(), is(notNullValue()));
		assertThat(manipel.getStudienrichtung(), is(equalTo(studienrichtung)));
		assertThat(manipel.getJahrgang(), is(equalTo(jahrgang)));
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testUniqueContraints() {
		Manipel manipel = new Manipel(jahrgang, studienrichtung);
		manipelDAO.makePersistent(manipel);
		Manipel manipel2 = new Manipel(jahrgang, studienrichtung);
		manipelDAO.makePersistent(manipel2);
	}
}
