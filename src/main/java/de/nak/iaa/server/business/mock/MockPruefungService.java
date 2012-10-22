package de.nak.iaa.server.business.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.common.base.Optional;

import de.nak.iaa.server.business.IllegalPruefungsleistungException;
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

	private final List<Pruefungsfach> pruefungsFaecher1;
	private final List<Pruefungsfach> pruefungsFaecher2;

	private final List<Pruefung> pruefungen;

	@SuppressWarnings("deprecation")
	public MockPruefungService() {
		pruefungsFaecher1 = new ArrayList<Pruefungsfach>();
		Pruefungsfach p1 = new Pruefungsfach("Modul1-9inf",
				"Modul1-9inf beschreibung",
				new Manipel(9, Studienrichtung.WInf));
		p1.setId((long) 1);
		pruefungsFaecher1.add(p1);
		Pruefungsfach p2 = new Pruefungsfach("Modul2-9inf",
				"Modul1-9inf beschreibung",
				new Manipel(9, Studienrichtung.WInf));
		p2.setId((long) 2);
		pruefungsFaecher1.add(p2);
		Pruefungsfach p3 = new Pruefungsfach("Modul3-9inf",
				"Modul1-9inf beschreibung",
				new Manipel(9, Studienrichtung.WInf));
		p3.setId((long) 3);
		pruefungsFaecher1.add(p3);

		pruefungsFaecher2 = new ArrayList<Pruefungsfach>();
		Pruefungsfach p4 = new Pruefungsfach("Modul1-8ing",
				"Modul1-8ing beschreibung",
				new Manipel(8, Studienrichtung.WIng));
		p4.setId((long) 4);
		pruefungsFaecher2.add(p4);
		Pruefungsfach p5 = new Pruefungsfach("Modul2-8ing",
				"Modul1-8ing beschreibung",
				new Manipel(8, Studienrichtung.WIng));
		p5.setId((long) 5);
		pruefungsFaecher2.add(p5);
		Pruefungsfach p6 = new Pruefungsfach("Modul3-8ing",
				"Modul1-8ing beschreibung",
				new Manipel(8, Studienrichtung.WIng));
		p6.setId((long) 6);
		pruefungsFaecher2.add(p6);

		pruefungen = new ArrayList<Pruefung>();
		Pruefung p7 = new Pruefung(new Date(2012, 6, 8), new Pruefungsfach(
				"Modul1-9inf", "Modul1-9inf beschreibung", new Manipel(9,
						Studienrichtung.WInf)));
		p7.setId((long) 1);
		pruefungen.add(p7);
		Pruefung p8 = new Pruefung(new Date(2012, 9, 15), new Pruefungsfach(
				"Modul1-9inf", "Modul1-9inf beschreibung", new Manipel(9,
						Studienrichtung.WInf)));
		p8.setId((long) 2);
		pruefungen.add(p8);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Pruefungsleistung> getAllPruefungsleistungen(
			Pruefungsfach pruefungsfach, Student student) {

		MockStudentService service = new MockStudentService();
		for (Pruefungsfach fach : pruefungsFaecher1) {
			if (fach.equals(pruefungsfach)) {
				if (service.getAllStudenten(fach.getManipel())
						.contains(student)) {
					List<Pruefungsleistung> list = new ArrayList<Pruefungsleistung>();
					list.add(new Pruefungsleistung(Versuch.Eins, new Date(2012,
							6, 8), pruefungen.get(0), null, student));
					list.add(new Pruefungsleistung(Versuch.Zwei, new Date(2012,
							9, 15), pruefungen.get(0), null, student));
					return list;
				}
			}
		}
		for (Pruefungsfach fach : pruefungsFaecher2) {
			if (fach.equals(pruefungsfach)) {
				if (service.getAllStudenten(fach.getManipel())
						.contains(student)) {
					List<Pruefungsleistung> list = new ArrayList<Pruefungsleistung>();
					list.add(new Pruefungsleistung(Versuch.Eins, new Date(2012,
							6, 8), pruefungen.get(1), null, student));
					list.add(new Pruefungsleistung(Versuch.Zwei, new Date(2012,
							9, 15), pruefungen.get(1), null, student));
					return list;
				}
			}
		}

		return null;
	}

	@Override
	public List<Pruefungsfach> getAllPruefungsfaecher(Manipel manipel) {
		if (manipel.equals(new Manipel(9, Studienrichtung.WInf))) {
			return pruefungsFaecher1;
		} else if (manipel.equals(new Manipel(8, Studienrichtung.WIng))) {
			return pruefungsFaecher2;
		}
		return null;
	}

	@Override
	public Optional<Pruefungsfach> getPruefungsfachById(Long id) {
		for (Pruefungsfach fach : pruefungsFaecher1) {
			if (fach.getId() == id)
				return Optional.of(fach);
		}
		for (Pruefungsfach fach : pruefungsFaecher2) {
			if (fach.getId() == id)
				return Optional.of(fach);
		}
		return Optional.absent();
	}

	@Override
	public List<Pruefung> getAllPruefungen(Pruefungsfach pruefungsfach) {
		return pruefungen;
	}

	@Override
	public Optional<Pruefung> getPruefungById(Long id) {
		for (Pruefung p : pruefungen) {
			if (p.getId() == id)
				return Optional.of(p);
		}
		return Optional.absent();
	}

	@Override
	public Pruefungsleistung addPruefungsleistung(Pruefung pruefung,
			Student student, Note note)
			throws IllegalPruefungsleistungException {
		if (new Random().nextBoolean()) {
			throw new IllegalPruefungsleistungException("Fehlernachricht");
		}
		return null;
	}
}
