package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungPersistenceTest extends
		ApplicationContextAwareTest {

	@Resource
	private PruefungsleistungDAO pruefungsleistungDao;

	@Test
	public void testVersionierung() {
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, new Date());
		pl = pruefungsleistungDao.makePersistent(pl);
		pl.setPruefungsDatum(new Date());
		pruefungsleistungDao.makePersistent(pl);
		Pruefungsleistung altePl = pruefungsleistungDao.getOldRevision(1,
				pl.getId());
		assertThat(altePl.getPruefungsDatum(),
				is(not(equalTo(pl.getPruefungsDatum()))));
	}
}
