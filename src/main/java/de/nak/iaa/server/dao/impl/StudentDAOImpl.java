package de.nak.iaa.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.StudentDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

/**
 * 
 * @author Ibrahim Karagac
 * 
 */

@Repository
public class StudentDAOImpl extends GenericHibernateDAOImpl<Student, Long>
		implements StudentDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findAllByManipel(Manipel manipel) {
		Query query = getSession().createQuery(
				"SELECT DISTINCT s FROM Student s WHERE s.manipel = :manipel");
		query.setParameter("manipel", manipel);
		return query.list();
	}

}