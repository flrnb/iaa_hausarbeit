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

	List<Manipel> getAllManipel();

	List<Student> getAllStudenten(Predicate<Student> filter);

	List<Student> getAllStudenten();

	List<Student> getAllStudenten(Manipel manipel);

}
