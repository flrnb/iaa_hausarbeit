package de.nak.iaa.server.business.impl;

import static de.nak.iaa.server.fachwert.Studienrichtung.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.server.dao.DAOMockBuilder;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

/**
 * Junit-Test für StudentService-Implementierung
 * 
 * @author flrnb
 */
public class StudentServiceImplTest {

	private StudentServiceImpl service;

	private static final Manipel man1 = new Manipel(2008, WInf);
	private static final Manipel man2 = new Manipel(2008, BWL);
	private static final Manipel man3 = new Manipel(2009, WIng);

	private static final Student student1 = new Student(1, man1, "1", "1");
	private static final Student student2 = new Student(2, man1, "2", "2");
	private static final Student student3 = new Student(3, man1, "3", "3");
	private static final Student student4 = new Student(4, man2, "4", "4");
	private static final Student student5 = new Student(5, man3, "5", "5");

	@Before
	public void setUp() {
		service = new StudentServiceImpl();
		ManipelDAO manipelDAO = DAOMockBuilder.forClass(ManipelDAO.class).addEntities(man1, man2, man3).build();

		StudentDAO studentDAO = DAOMockBuilder.forClass(StudentDAO.class)
				.addEntities(student1, student2, student3, student4, student5).build();
		service.setManipelDAO(manipelDAO);
		service.setStudentDAO(studentDAO);
	}

	@Test
	public void testGetAllManipel() {
		assertThat(service.getAllManipel(), hasItems(man1, man2, man3));
	}

	@Test
	public void testGetAllStudentenByManipel() {
		assertThat(service.getAllStudenten(man1), hasItems(student1, student2, student3));
		assertThat(service.getAllStudenten(man2), hasItems(student4));
		assertThat(service.getAllStudenten(man3), hasItems(student5));
	}

	@Test
	public void testGetAllStudenten() {
		assertThat(service.getAllStudenten(), hasItems(student1, student2, student3, student4, student5));
	}
}
