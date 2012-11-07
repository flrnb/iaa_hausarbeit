package de.nak.iaa.server.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.business.PruefungsleistungHistoryEntry;
import de.nak.iaa.server.entity.Dozent;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungDAOImplTest extends ApplicationContextAwareTest {
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
	protected void doSetUp() {
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

	public <T> T makePersistentInTransaction(final T toPersist,
			final GenericDAO<T, Long> dao) {
		return transactionTemplate.execute(new TransactionCallback<T>() {

			@Override
			public T doInTransaction(TransactionStatus status) {
				return dao.makePersistent(toPersist);
			}
		});
	};

	@Test
	public void testGetAltePruefungsleistungenEinfach() {
		Versuch versuch = Versuch.Eins;
		Pruefungsleistung pl = new Pruefungsleistung(versuch, pruefung,
				Note.Drei, student);
		makePersistentInTransaction(pl, pruefungsleistungDAO);
		List<PruefungsleistungHistoryEntry> altePruefungsleistungen = pruefungsleistungDAO
				.getAltePruefungsleistungen(student, pruefungsfach, versuch);
		assertThat(altePruefungsleistungen.size(), is(equalTo(1)));
		altePruefungsleistungen = pruefungsleistungDAO
				.getAltePruefungsleistungen(student, pruefungsfach, versuch
						.next().get());
		assertThat(altePruefungsleistungen.size(), is(equalTo(0)));
	}

	@Test
	public void testGetAltePruefungsleistungenhMehrere() {
		Versuch versuch = Versuch.Eins;
		Pruefungsleistung pl = new Pruefungsleistung(versuch, pruefung,
				Note.Drei, student);
		makePersistentInTransaction(pl, pruefungsleistungDAO);
		Pruefung pruefung2 = new Pruefung(new Date(), pruefungsfach, dozent);
		makePersistentInTransaction(pruefung2, pruefungDAO);
		Pruefungsleistung pl2 = new Pruefungsleistung(versuch, pruefung2,
				Note.DreiDrei, student);
		makePersistentInTransaction(pl2, pruefungsleistungDAO);
		List<PruefungsleistungHistoryEntry> altePruefungsleistungen = pruefungsleistungDAO
				.getAltePruefungsleistungen(student, pruefungsfach, versuch);
		assertThat(altePruefungsleistungen.size(), is(equalTo(2)));
	}
}
