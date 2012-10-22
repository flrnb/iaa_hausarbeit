package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.entity.Student;

@Repository
public class StudentDAOImpl extends GenericHibernateDAOImpl<Student, Long>
		implements StudentDAO {

}