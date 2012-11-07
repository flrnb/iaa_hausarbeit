package de.nak.iaa.server.entity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import de.nak.iaa.TransactionalApplicationContextAwareTest;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.fachwert.Studienrichtung;

public class StudentPersistenceTest extends TransactionalApplicationContextAwareTest {

	@Resource
	private StudentDAO studentDAO;
	@Resource
	private ManipelDAO manipelDAO;

	private Manipel manipel;

	@Before
	public void setUp() {
		manipel = new Manipel(2006, Studienrichtung.BWL);
		manipelDAO.makePersistent(manipel);
	}

	@Test
	public void testPersistStudent() {
		int countBefore = studentDAO.findAll().size();
		Student student = new Student(1, manipel, "name", "vorname");
		studentDAO.makePersistent(student);
		int countAfter = studentDAO.findAll().size();
		assertThat(countAfter, is(equalTo(countBefore + 1)));
	}

	@Test
	public void testFindStudentByManipel() {
		Manipel anderesManipel = new Manipel(2006, Studienrichtung.WInf);
		anderesManipel = manipelDAO.makePersistent(anderesManipel);
		Student s1 = new Student(1, manipel, "name", "vorname");
		Student s2 = new Student(2, manipel, "name2", "vorname2");
		Student s3 = new Student(3, anderesManipel, "name3", "vorname3");
		studentDAO.makePersistent(s1);
		studentDAO.makePersistent(s2);
		studentDAO.makePersistent(s3);
		List<Student> list = studentDAO.findAllByManipel(manipel);
		assertThat(list.size(), is(equalTo(2)));
		assertThat(list.contains(s3), is(Boolean.FALSE));
	}
}
