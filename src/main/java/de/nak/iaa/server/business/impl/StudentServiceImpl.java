package de.nak.iaa.server.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

/**
 * Implementierung von {@link StudentService}
 * 
 * @author flrnb
 */
public class StudentServiceImpl implements StudentService {

	@Autowired
	private ManipelDAO manipelDAO;

	@Autowired
	private StudentDAO studentDAO;

	@Override
	public List<Manipel> getAllManipel() {
		return ImmutableList.copyOf(manipelDAO.findAll());
	}

	public void setManipelDAO(ManipelDAO manipelDAO) {
		this.manipelDAO = manipelDAO;
	}

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	@Override
	public List<Student> getAllStudenten(Predicate<Student> filter) {
		return ImmutableList.copyOf(Iterables.filter(getAllStudenten(), filter));
	}

	@Override
	public List<Student> getAllStudenten() {
		return studentDAO.findAll();
	}

	@Override
	public List<Student> getAllStudenten(final Manipel manipel) {
		return getAllStudenten(new Predicate<Student>() {
			@Override
			public boolean apply(Student s) {
				return s.getManipel().equals(manipel);
			}
		});
	}
}
