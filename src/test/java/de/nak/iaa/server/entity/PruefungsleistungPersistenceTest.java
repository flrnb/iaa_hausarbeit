package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.DozentDAO;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungPersistenceTest extends ApplicationContextAwareTest {

	@Resource
	private PruefungsleistungDAO pruefungsleistungDAO;
	@Resource
	private PruefungDAO pruefungDAO;
	@Resource
	private PruefungsfachDAO pruefungsfachDAO;
	@Resource
	private ManipelDAO manipelDAO;
	@Resource
	private StudentDAO studentDAO;
	@Resource
	private DozentDAO dozentDAO;
	private Pruefungsfach pruefungsfach;
	private Pruefung pruefung;
	private Student student;
	private Dozent dozent;

	@Override
	public void doSetUp() {
		Manipel manipel = new Manipel(2007, Studienrichtung.BWL);
		manipel = manipelDAO.makePersistent(manipel);
		dozent = new Dozent("b", "a");
		dozent = dozentDAO.makePersistent(dozent);
		pruefungsfach = new Pruefungsfach("Titel", "beschreibung", manipel);
		pruefungsfach = pruefungsfachDAO.makePersistent(pruefungsfach);
		pruefung = new Pruefung(new Date(), pruefungsfach, dozent);
		pruefung = pruefungDAO.makePersistent(pruefung);
		student = new Student(1, manipel, "Name", "Vorname");
		student = studentDAO.makePersistent(student);
	}

	@Test
	public void testPersistierung() {
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				int countBefore = pruefungsleistungDAO.findAll().size();
				Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, pruefung, Note.Drei, student);
				makePersistentInTransaction(pl);

				int countAfter = pruefungsleistungDAO.findAll().size();
				assertThat(countBefore + 1, is(equalTo(countAfter)));
				return null;
			}
		});
	}

	@Test
	public void testPersistierungMitErgaenzungspruefung() {
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, pruefung, Note.EinsDrei, student);
				pl.setErgaenzungsPruefung(new ErgaenzungsPruefung(Note.Drei, new Date()));
				makePersistentInTransaction(pl);
				Long id = pl.getId();
				pl = pruefungsleistungDAO.findById(id, false);
				assertThat(pl.getErgaenzungsPruefung().getId(), is(notNullValue()));
				return null;
			}
		});
	}

	@Test
	public void testGetAltePruefungsleistungen() {
		final Pruefungsleistung pl = erzeugePruefungsleistung();
		veraenderePruefungsleistung(pl);
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				Map<Pruefungsleistung, Date> altePruefungsleistungen = pruefungsleistungDAO
						.getAltePruefungsleistungen(pl.getId());
				assertThat(altePruefungsleistungen.isEmpty(), is(Boolean.FALSE));
				return null;
			}
		});
	}

	private void veraenderePruefungsleistung(Pruefungsleistung pl) {
		pl.setNote(Note.Eins);
		makePersistentInTransaction(pl);
	}

	private Pruefungsleistung erzeugePruefungsleistung() {
		Pruefungsleistung pl = new Pruefungsleistung(Versuch.Eins, pruefung, Note.Drei, student);
		return makePersistentInTransaction(pl);
	}

	public Pruefungsleistung makePersistentInTransaction(final Pruefungsleistung leistung) {
		return transactionTemplate.execute(new TransactionCallback<Pruefungsleistung>() {

			@Override
			public Pruefungsleistung doInTransaction(TransactionStatus status) {
				return pruefungsleistungDAO.makePersistent(leistung);
			}
		});
	};
}
