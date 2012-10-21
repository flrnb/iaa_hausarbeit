package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungPersistenceTest extends
		ApplicationContextAwareTest {

	@Resource
	private PruefungsleistungDAO pruefungsleistungDao;
	@Resource
	HibernateTransactionManager transactionManager;

	@Before
	public void setUp() {
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, new Date());
		pruefungsleistungDao.makePersistent(pl);
	}

	@Test
	public void testPersistierung() {
		int countBefore = pruefungsleistungDao.findAll().size();
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, new Date());
		pruefungsleistungDao.makePersistent(pl);

		int countAfter = pruefungsleistungDao.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
	}

	@Test
	public void testPersistierungMitErgaenzungspruefung() {
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, new Date());
		pl.setErgaenzungsPruefung(new ErgaenzungsPruefung(Note.Drei, new Date()));
		pl = pruefungsleistungDao.makePersistent(pl);
		assertThat(pl.getErgaenzungsPruefung(),
				is(notNullValue(ErgaenzungsPruefung.class)));
	}
}
