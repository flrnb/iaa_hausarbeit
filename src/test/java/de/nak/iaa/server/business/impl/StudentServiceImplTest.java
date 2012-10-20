package de.nak.iaa.server.business.impl;

import static de.nak.iaa.server.fachwert.Studienrichtung.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.server.dao.DAOMockBuilder;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.entity.Manipel;

/**
 * Junit-Test für StudentService-Implementierung
 * 
 * @author flrnb
 */
public class StudentServiceImplTest {

	private StudentServiceImpl service;

	private List<Manipel> allManipel;

	@Before
	public void setUp() {
		service = new StudentServiceImpl();
		// @formatter:off
		allManipel = Arrays.asList(
				new Manipel(2008, WInf),
				new Manipel(2008, BWL),
				new Manipel(2008, WIng),
				new Manipel(2009, WInf),
				new Manipel(2009, BWL),
				new Manipel(2009, WIng));
		// @formatter:on
		ManipelDAO manipelDAO = DAOMockBuilder.forClass(ManipelDAO.class)
				.addEntities(allManipel).build();
		service.setManipelDAO(manipelDAO);
	}

	@Test
	public void testGetAllManipel() {
		assertThat(service.getAllManipel(),
				hasItems(allManipel.toArray(new Manipel[0])));
	}
}
