package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Before;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
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

	// @Test
	public void testVersionierung() {
		PlatformTransactionManager txMgr = transactionManager;

		// A new transaction is required, the wrapping transaction is for Envers
		TransactionStatus status = txMgr
				.getTransaction(new DefaultTransactionDefinition(
						TransactionDefinition.PROPAGATION_REQUIRES_NEW));

		// ein Tag abziehen, da das Datum in der DB die Genauigkeit Datum hat
		Pruefungsleistung pruefungsleistung = pruefungsleistungDao.findAll()
				.get(0);
		pruefungsleistung.setPruefungsDatum(new Date(pruefungsleistung
				.getPruefungsDatum().getTime()
				- TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS)));

		txMgr.commit(status);
		// pruefungsleistungDao.makePersistent(pruefungsleistung);
		Pruefungsleistung altePl = pruefungsleistungDao.getOldRevision(1,
				pruefungsleistung.getId());
		assertThat(altePl.getPruefungsDatum(),
				is(not(equalTo(pruefungsleistung.getPruefungsDatum()))));
	}
}
