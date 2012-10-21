package de.nak.iaa.server.business.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.dao.DAOMockBuilder;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;

/**
 * JUnit-Test für Implementierung von {@link PruefungService}
 * 
 * @author flrnb
 */
public class PruefungServiceImplTest {

	private PruefungServiceImpl service;

	private Pruefungsfach fach1;

	private Pruefungsfach fach2;

	private Pruefungsfach fach3;

	private Manipel man1;

	private Manipel man2;

	@Before
	public void setUp() {
		service = new PruefungServiceImpl();
		man1 = new Manipel(2009, Studienrichtung.WInf);
		man2 = new Manipel(2010, Studienrichtung.WInf);
		fach1 = new Pruefungsfach("Fach1", "Beschreibung1", man1);
		fach2 = new Pruefungsfach("Fach2", "Beschreibung2", man1);
		fach3 = new Pruefungsfach("Fach3", "Beschreibung3", man2);
		PruefungsfachDAO pruefungsfachDAO = DAOMockBuilder.forClass(PruefungsfachDAO.class)
				.addEntities(fach1, fach2, fach3).build();
		service.setPruefungsfachDAO(pruefungsfachDAO);
	}

	@Test
	public void testGetAllPruefungsfaecher() {
		assertThat(service.getAllPruefungsfaecher(man1), hasItems(fach1, fach2));
		assertThat(service.getAllPruefungsfaecher(man2), hasItems(fach3));
	}

	@Test
	public void testUpdatePruefungsleistung() {
		Student student = new Student();
		Pruefung pruefung = new Pruefung();
		service.addPruefungsleistung(pruefung, new Date(), student, Note.EinsDrei);
		List<Pruefungsleistung> allPruefungsleistungen = service.getAllPruefungsleistungen(fach1, student);
		assertThat(allPruefungsleistungen.size(), is(1));
		Pruefungsleistung leistung = allPruefungsleistungen.get(0);
		service.updatePruefungsleistung(leistung.getId(), Note.EinsSieben);
		// assertThat(leistung.getNote(), is(Note.EinsSieben))
	}

}
