package de.nak.iaa.server.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

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

	List<Pruefungsleistung> getAllPruefungsleistungen(Pruefungsfach fach, Student student);

	/**
	 * @param manipel
	 * @param fach
	 * @return alle Studenten, bei denen aktuell eine Ergänzungsprüfung erfasst
	 *         werden kann mit dem Datum der zu ergänzenden Prüfungsleistung
	 */
	Map<Student, Date> getAllErgaenzungsPruefungsStudenten(Manipel manipel, Pruefungsfach fach);

	Map<Student, Optional<Pruefungsleistung>> getAllStudentenForPruefung(Pruefung pruefung, Student student);

	/**
	 * Für einen Studenten eine neue Prüfungsleistung erfassen
	 * 
	 * @param pruefung
	 * @param datum
	 * @param student
	 * @param note
	 * @throws IllegalPruefungsleistungException
	 *             wenn die Prüfungsleistung nicht zulässig ist
	 */
	void addPruefungsleistung(Pruefung pruefung, Date datum, Student student, Note note);

}
