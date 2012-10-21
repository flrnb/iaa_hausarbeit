package de.nak.iaa.server.business;

import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;

public interface StudentService {

	List<Manipel> getAllManipel();

	List<Student> getAllStudenten(Manipel manipel);

}
