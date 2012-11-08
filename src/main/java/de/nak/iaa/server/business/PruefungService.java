package de.nak.iaa.server.business;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.javatuples.Triplet;

import com.google.common.base.Optional;

import de.nak.iaa.server.entity.Dozent;
import de.nak.iaa.server.entity.ErgaenzungsPruefung;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * Service für pruefungs- und pruefungsleistungsbezogene Aufgaben
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
public interface PruefungService {

	/**
	 * @param manipel
	 * @return Liste aller Prüfungsfächer für dieses Manipel
	 */
	List<Pruefungsfach> getAllPruefungsfaecher(Manipel manipel);

	/**
	 * @param id
	 * @return Prüfungsfach mit der übergebenen ID, falls vorhanden
	 */
	Optional<Pruefungsfach> getPruefungsfachById(Long id);

	/**
	 * @param manipel
	 * @param pruefungsfach
	 * @return alle stattgefundenen und geplanten Prüfungen zu einem
	 *         Prüfungsfach
	 */
	List<Pruefung> getAllPruefungen(Pruefungsfach pruefungsfach);

	/**
	 * @param id
	 * @return Prüfung mit der übergebenen ID, falls vorhanden
	 */
	Optional<Pruefung> getPruefungById(Long id);

	/**
	 * legt eine neue Prüfung am übergebenen Termin an
	 * 
	 * @param fach
	 * @param datum
	 * @return neu angelegte Prüfung
	 */
	Pruefung addPruefung(Pruefungsfach fach, Date datum, Dozent dozent);

	/**
	 * @param fach
	 * @param student
	 * @return alle bisherigen Prüfungsleistungen eines Studenten in einem
	 *         Prüfungsfach
	 */
	List<Pruefungsleistung> getAllPruefungsleistungen(Pruefungsfach fach, Student student);

	/**
	 * @param id
	 *            der Prüfungsleistung
	 * @return true wenn eine nachträgliche Änderung an der Prüfungsleistung
	 *         noch zulässig ist
	 * @throws IllegalArgumentException
	 *             falls id nicht vergeben
	 */
	boolean isPruefungsleistungEditable(Long id);

	/**
	 * führt die Liste der übergebenen Änderungsaufgaben durch
	 * 
	 * @param aenderungen
	 * @throws IllegalUpdateException
	 *             falls eine Änderung nicht zulässig ist
	 */
	void updatePruefungsleistungen(List<? extends PruefungsleistungAenderung> aenderungen)
			throws IllegalUpdateException;

	/**
	 * legt alle übergebenen Prüfungsleistungen in einer Transaktion an
	 * 
	 * @param leistungen
	 * @throws IllegalUpdateException
	 *             falls mind. eine Prüfungsleistung nicht zulässig ist
	 */
	void addPruefungsleistungen(List<Triplet<Pruefung, Student, Note>> leistungen) throws IllegalUpdateException;

	/**
	 * @param manipel
	 * @param fach
	 * @return alle Studenten, bei denen aktuell eine Ergänzungsprüfung erfasst
	 *         werden kann mit dem Datum der zu ergänzenden Prüfungsleistung
	 */
	Map<Student, Date> getAllErgaenzungsPruefungsStudenten(Manipel manipel, Pruefungsfach fach);

	/**
	 * 
	 * @param pruefung
	 * @param student
	 * @return Liste aller Studenten des Manipels mit der letzten
	 *         Prüfungsleistung für die übergebene Prüfung, falls eine vorhanden
	 *         ist
	 */
	Map<Student, Optional<Pruefungsleistung>> getAllStudentenForPruefung(Pruefung pruefung);

	/**
	 * @param student
	 * @param fach
	 * @return die aktuell gültige {@link Note}, falls vorhanden
	 */
	Optional<Note> getAktuelleNote(Student student, Pruefungsfach fach);

	/**
	 * für eine Prüfungsleistung eine Ergänzungsprüfung erfassen
	 * 
	 * @require isErgaenzungsPruefungZulaessig(pruefungsleistung)
	 * @param student
	 * @param fach
	 * @param prozent
	 * @param datum
	 *            (an dem die Prüfung stattgefunden hat)
	 * @return die neu erstellte {@link ErgaenzungsPruefung}
	 * @throws {@link IllegalStateException} falls keine Ergänzungsprüfung
	 *         zulässig ist
	 */
	ErgaenzungsPruefung addErgaenzungsPruefung(Student student, Pruefungsfach fach, Date datum, int prozent);

	/**
	 * ermittelt die Historie der Prüfungsleistungen für einen Studenten und ein
	 * Prüfungsfach
	 * 
	 * @param student
	 * @param fach
	 * @return für jeden Versuch alle jemals erfassten Prüfungsleistungen, nach
	 *         ihrem Datum sortiert
	 * @see PruefungsleistungHistoryEntry
	 */
	Map<Versuch, SortedSet<PruefungsleistungHistoryEntry>> getPruefungsleistungHistorie(Student student,
			Pruefungsfach fach);

}
