package de.nak.iaa.server.dao;

import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

public interface StudentDAO extends GenericDAO<Student, Long> {

	List<Student> findAllByManipel(Manipel manipel);
}
