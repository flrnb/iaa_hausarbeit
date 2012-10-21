package de.nak.iaa.server.business.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.impl.PruefungServiceImpl;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.server.fachwert.Versuch;

public class MockPruefungService extends PruefungServiceImpl implements
		PruefungService {

	private final List<Pruefungsfach> list1;
	private final List<Pruefungsfach> list2;

	public MockPruefungService() {
		list1 = new ArrayList<Pruefungsfach>();
		Pruefungsfach p1 = new Pruefungsfach("Modul1-9inf",
				"Modul1-9inf beschreibung",
				new Manipel(9, Studienrichtung.WInf));
		p1.setId((long) 1);
		list1.add(p1);
		Pruefungsfach p2 = new Pruefungsfach("Modul2-9inf",
				"Modul1-9inf beschreibung",
				new Manipel(9, Studienrichtung.WInf));
		p2.setId((long) 2);
		list1.add(p2);
		Pruefungsfach p3 = new Pruefungsfach("Modul3-9inf",
				"Modul1-9inf beschreibung",
				new Manipel(9, Studienrichtung.WInf));
		p3.setId((long) 3);
		list1.add(p3);

		list2 = new ArrayList<Pruefungsfach>();
		Pruefungsfach p4 = new Pruefungsfach("Modul1-8ing",
				"Modul1-8ing beschreibung",
				new Manipel(8, Studienrichtung.WIng));
		p4.setId((long) 4);
		list2.add(p4);
		Pruefungsfach p5 = new Pruefungsfach("Modul2-8ing",
				"Modul1-8ing beschreibung",
				new Manipel(8, Studienrichtung.WIng));
		p5.setId((long) 5);
		list2.add(p5);
		Pruefungsfach p6 = new Pruefungsfach("Modul3-8ing",
				"Modul1-8ing beschreibung",
				new Manipel(8, Studienrichtung.WIng));
		p6.setId((long) 6);
		list2.add(p6);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Pruefungsleistung> getAllPruefungsleistungen(
			Pruefungsfach pruefungsfach, Student student) {

		MockStudentService service = new MockStudentService();
		for (Pruefungsfach fach : list1) {
			if (fach.equals(pruefungsfach)) {
				if (service.getAllStudenten(fach.getManipel())
						.contains(student)) {
					List<Pruefungsleistung> list = new ArrayList<Pruefungsleistung>();
					list.add(new Pruefungsleistung(Versuch.Eins, new Date(2012,
							6, 8), new Pruefung(new Date(), new Pruefungsfach(
							"Titel", "beschreibung", new Manipel(2009,
									Studienrichtung.WInf))), Note.Drei));
					list.add(new Pruefungsleistung(Versuch.Zwei, new Date(2012,
							9, 15), new Pruefung(new Date(), new Pruefungsfach(
							"Titel", "beschreibung", new Manipel(2009,
									Studienrichtung.WInf))), Note.DreiDrei));
					return list;
				}
			}
		}
		for (Pruefungsfach fach : list2) {
			if (fach.equals(pruefungsfach)) {
				if (service.getAllStudenten(fach.getManipel())
						.contains(student)) {
					List<Pruefungsleistung> list = new ArrayList<Pruefungsleistung>();
					list.add(new Pruefungsleistung(Versuch.Eins, new Date(2012,
							6, 8), new Pruefung(new Date(), new Pruefungsfach(
							"Titel", "beschreibung", new Manipel(2009,
									Studienrichtung.WInf))), Note.Eins));
					list.add(new Pruefungsleistung(Versuch.Zwei, new Date(2012,
							9, 15), new Pruefung(new Date(), new Pruefungsfach(
							"Titel", "beschreibung", new Manipel(2009,
									Studienrichtung.WInf))), Note.Zwei));
					return list;
				}
			}
		}

		return null;
	}

	@Override
	public List<Pruefungsfach> getAllPruefungsfaecher(Manipel manipel) {
		if (manipel.equals(new Manipel(9, Studienrichtung.WInf))) {
			return list1;
		} else if (manipel.equals(new Manipel(8, Studienrichtung.WIng))) {
			return list2;
		}
		return null;
	}

	@Override
	public Pruefungsfach getPruefungsfachById(Long id) {
		for (Pruefungsfach fach : list1) {
			System.out.println(fach.getId() + "::" + id);
			if (fach.getId() == id)
				return fach;
		}
		for (Pruefungsfach fach : list2) {
			System.out.println(fach.getId() + "::" + id);
			if (fach.getId() == id)
				return fach;
		}
		return null;
	}
}
