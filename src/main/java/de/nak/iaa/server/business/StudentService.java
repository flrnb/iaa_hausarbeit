package de.nak.iaa.server.business;

import java.util.List;

import com.google.common.base.Predicate;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

/**
 * Service für Studentenbezogene Funktionen
 * 
 * @author flrnb
 */
public interface StudentService {

	/**
	 * liefert eine Liste aller {@link Manipel} im System
	 * 
	 * @return Liste aller Manipel
	 */
	List<Manipel> getAllManipel();

	/**
	 * liefert eine Liste aller Student, auf das übergebene Prädikat zutrifft
	 * 
	 * @param filter
	 * @return gefilterte Liste aller Studenten
	 */
	List<Student> getAllStudenten(Predicate<Student> filter);

	/**
	 * liefert eine Liste aller Studenten im System
	 * 
	 * @return Liste aller Studenten
	 */
	List<Student> getAllStudenten();

	/**
	 * liefert eine Liste aller Studenten des übergebenen Manipels
	 * 
	 * @param manipel
	 * @return alle Studenten aus manipel
	 */
	List<Student> getAllStudenten(Manipel manipel);

}
