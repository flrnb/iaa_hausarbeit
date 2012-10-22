package de.nak.iaa.server.business;

import java.util.Date;
import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;

/**
 * Service für pruefungs- und pruefungsleistungsbezogene Aufgaben
 * 
 * @author flrnb
 */
public interface PruefungService {

	List<Pruefungsfach> getAllPruefungsfaecher(Manipel manipel);

	Pruefungsfach getPruefungsfachById(Long id);

	void updatePruefungsleistung(Long id, Note note);

	List<Pruefungsleistung> getAllPruefungsleistungen(Pruefungsfach fach,
			Student student);

	List<Student> getAllErgaenzungsPruefungsStudenten(Manipel manipel,
			Pruefungsfach fach);

	void addPruefungsleistung(Pruefung pruefung, Date datum, Student student,
			Note note);

	List<Pruefung> getAllPruefung(Manipel manipel, Pruefungsfach pruefungsfach);

	Pruefung getPruefungById(Long id);
}
