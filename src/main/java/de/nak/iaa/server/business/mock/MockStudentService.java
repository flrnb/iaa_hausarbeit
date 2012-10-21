package de.nak.iaa.server.business.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.business.impl.StudentServiceImpl;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Studienrichtung;

public class MockStudentService extends StudentServiceImpl implements StudentService {

	@SuppressWarnings("unused")
	@Autowired
	private ManipelDAO manipelDAO;

	@Override
	public void setManipelDAO(ManipelDAO manipelDAO) {
		this.manipelDAO = manipelDAO;
	}

	@Override
	public List<Manipel> getAllManipel() {
		List<Manipel> list = new ArrayList<Manipel>();
		list.add(new Manipel(9, Studienrichtung.BWL));
		list.add(new Manipel(9, Studienrichtung.WInf));
		list.add(new Manipel(9, Studienrichtung.WIng));
		list.add(new Manipel(8, Studienrichtung.BWL));
		list.add(new Manipel(8, Studienrichtung.WInf));
		list.add(new Manipel(8, Studienrichtung.WIng));

		return list;
	}

	@Override
	public List<Student> getAllStudenten(Manipel manipel) {
		Map<Manipel, List<Student>> map = new HashMap<Manipel, List<Student>>();

		Manipel m1 = new Manipel(9, Studienrichtung.BWL);
		List<Student> list1 = new ArrayList<Student>();
		list1.add(new Student(11111, "eins", "09"));
		list1.add(new Student(11112, "zwei", "09"));
		list1.add(new Student(11113, "drei", "09"));
		map.put(m1, list1);

		Manipel m2 = new Manipel(9, Studienrichtung.WInf);
		List<Student> list2 = new ArrayList<Student>();
		list2.add(new Student(11114, "eins", "08"));
		list2.add(new Student(11115, "zwei", "08"));
		list2.add(new Student(11116, "drei", "08"));
		map.put(m2, list2);

		Manipel m3 = new Manipel(8, Studienrichtung.WIng);
		List<Student> list3 = new ArrayList<Student>();
		list3.add(new Student(11117, "eins", "08"));
		list3.add(new Student(11118, "zwei", "08"));
		list3.add(new Student(11119, "drei", "08"));
		map.put(m3, list3);

		if (map.containsKey(manipel)) {
			return map.get(manipel);
		}
		return null;
	}
}
