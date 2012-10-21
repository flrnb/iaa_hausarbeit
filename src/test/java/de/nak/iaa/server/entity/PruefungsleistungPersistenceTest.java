package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungPersistenceTest extends
		ApplicationContextAwareTest {

	@Resource
	private PruefungsleistungDAO pruefungsleistungDAO;
	@Resource
	private PruefungDAO pruefungDAO;
	@Resource
	private PruefungsfachDAO pruefungsfachDAO;
	@Resource
	private ManipelDAO manipelDAO;
	private Pruefungsfach pruefungsfach;
	private Pruefung pruefung;

	@Before
	public void setUp() {
		pruefungsfach = new Pruefungsfach("Titel", "beschreibung", manipelDAO
				.findAll().get(0));
		pruefungsfach = pruefungsfachDAO.makePersistent(pruefungsfach);
		pruefung = new Pruefung(new Date(), pruefungsfach);
		pruefung = pruefungDAO.makePersistent(pruefung);
	}

	@Test
	public void testPersistierung() {
		int countBefore = pruefungsleistungDAO.findAll().size();
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, new Date(),
				pruefung);
		pruefungsleistungDAO.makePersistent(pl);

		int countAfter = pruefungsleistungDAO.findAll().size();
		assertThat(countBefore + 1, is(equalTo(countAfter)));
	}

	@Test
	public void testPersistierungMitErgaenzungspruefung() {
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, new Date(),
				pruefung);
		pl.setErgaenzungsPruefung(new ErgaenzungsPruefung(Note.Drei, new Date()));
		pl = pruefungsleistungDAO.makePersistent(pl);
		assertThat(pl.getErgaenzungsPruefung(),
				is(notNullValue(ErgaenzungsPruefung.class)));
	}
}
