package de.nak.iaa.server.business.impl;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.dao.DAOMockBuilder;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;
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

	@Before
	public void setUp() {
		service = new PruefungServiceImpl();
		fach1 = new Pruefungsfach("Fach1", "Beschreibung1", new Manipel(2009, Studienrichtung.WInf));
		fach2 = new Pruefungsfach("Fach2", "Beschreibung2", new Manipel(2009, Studienrichtung.WInf));
		fach3 = new Pruefungsfach("Fach3", "Beschreibung3", new Manipel(2010, Studienrichtung.WInf));
		PruefungsfachDAO pruefungsfachDAO = DAOMockBuilder.forClass(PruefungsfachDAO.class)
				.addEntities(fach1, fach2, fach3).build();
		service.setPruefungsfachDAO(pruefungsfachDAO);
	}

	@Test
	public void testGetAllPruefungsfaecher() {
		assertThat(service.getAllPruefungsfaecher(), hasItems(fach1, fach2, fach3));
	}

}
