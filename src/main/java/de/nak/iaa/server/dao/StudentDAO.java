package de.nak.iaa.server.dao;

import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

/**
 * Interface für ein DAO, welches sich um {@link Student} kümmert.
 * 
 * @author Ibrahim Karagac
 * 
 */
public interface StudentDAO extends GenericDAO<Student, Long> {

	List<Student> findAllByManipel(Manipel manipel);
}
