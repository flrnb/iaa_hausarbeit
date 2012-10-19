package de.nak.iaa.server.business.impl;

import static de.nak.iaa.server.fachwert.Studienrichtung.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.entity.Manipel;

public class StudentServiceImplTest {

	private StudentServiceImpl service;

	private Map<Long, Manipel> allManipel;

	@Before
	public void setUp() {
		service = new StudentServiceImpl();
		allManipel = new HashMap<Long, Manipel>();
		allManipel.put(1L, new Manipel(2008, WInf));
		allManipel.put(2L, new Manipel(2008, BWL));
		allManipel.put(3L, new Manipel(2008, WIng));
		allManipel.put(4L, new Manipel(2009, WInf));
		allManipel.put(5L, new Manipel(2009, BWL));
		allManipel.put(6L, new Manipel(2009, WIng));
		ManipelDAO manipelDAO = EasyMock.createNiceMock(ManipelDAO.class);
		EasyMock.expect(manipelDAO.findAll())
				.andReturn(new ArrayList<Manipel>(allManipel.values()))
				.anyTimes();
		EasyMock.expect(
				manipelDAO.findById(EasyMock.anyLong(), EasyMock.anyBoolean()))
				.andAnswer(new IAnswer<Manipel>() {
					@Override
					public Manipel answer() throws Throwable {
						return allManipel.get(EasyMock.getCurrentArguments()[0]);
					}
				});
		EasyMock.replay(manipelDAO);
		service.setManipelDAO(manipelDAO);

	}

	@Test
	public void testGetAllManipel() {
		assertThat(service.getAllManipel(), hasItems(allManipel.values()
				.toArray(new Manipel[0])));
	}

}
