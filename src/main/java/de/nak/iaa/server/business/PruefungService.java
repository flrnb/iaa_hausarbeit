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

	/**
	 * @param manipel
	 * @return Liste aller Prüfungsfächer für dieses Manipel
	 */
	List<Pruefungsfach> getAllPruefungsfaecher(Manipel manipel);

	/**
	 * @param id
	 * @return Prüfungsfach mit der übergebenen ID
	 */
	Pruefungsfach getPruefungsfachById(Long id);

	/**
	 * @param manipel
	 * @param pruefungsfach
	 * @return alle stattgefundenen und geplanten Prüfungen zu einem
	 *         Prüfungsfach
	 */
	List<Pruefung> getAllPruefungen(Pruefungsfach pruefungsfach);

	/**
	 * @param id
	 * @return Prüfung mit der übergebenen ID
	 */
	Pruefung getPruefungById(Long id);

	/**
	 * legt eine neue Prüfung am übergebenen Termin an
	 * 
	 * @param fach
	 * @param datum
	 */
	void addPruefung(Pruefungsfach fach, Date datum);

	/**
	 * @param fach
	 * @param student
	 * @return alle bisherigen Prüfungsleistungen eines Studenten in einem
	 *         Prüfungsfach
	 */
	List<Pruefungsleistung> getAllPruefungsleistungen(Pruefungsfach fach,
			Student student);

	/**
	 * @param id
	 *            der Prüfungsleistung
	 * @return true wenn eine nachträgliche Änderung an der Prüfungsleistung
	 *         noch zulässig ist
	 */
	boolean isPruefungsleistungEditable(Long id);

	/**
	 * @require isPruefungsleistungEditable(id)
	 * @param id
	 * @param note
	 * @trows {@link IllegalStateException} wenn Prüfungsleistung nicht
	 *        editierbar ist
	 */
	void updatePruefungsleistung(Long id, Note note);

	/**
	 * Für einen Studenten eine neue Prüfungsleistung erfassen
	 * 
	 * @param pruefung
	 * @param student
	 * @param note
	 * @throws IllegalPruefungsleistungException
	 *             wenn die Prüfungsleistung nicht zulässig ist
	 */
	void addPruefungsleistung(Pruefung pruefung, Student student, Note note);

	/**
	 * @param manipel
	 * @param fach
	 * @return alle Studenten, bei denen aktuell eine Ergänzungsprüfung erfasst
	 *         werden kann mit dem Datum der zu ergänzenden Prüfungsleistung
	 */
	Map<Student, Date> getAllErgaenzungsPruefungsStudenten(Manipel manipel,
			Pruefungsfach fach);

	/**
	 * 
	 * @param pruefung
	 * @param student
	 * @return Liste aller Studenten des Manipels mit der letzten
	 *         Prüfungsleistung für die übergebene Prüfung, falls eine vorhanden
	 *         ist
	 */
	Map<Student, Optional<Pruefungsleistung>> getAllStudentenForPruefung(
			Pruefung pruefung);

	/**
	 * @param student
	 * @param fach
	 * @return die aktuell gültige Note, falls vorhanden
	 */
	Optional<Note> getAktuelleNote(Student student, Pruefungsfach fach);
}
