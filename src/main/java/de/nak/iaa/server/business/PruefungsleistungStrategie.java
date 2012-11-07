package de.nak.iaa.server.business;

import com.google.common.base.Optional;

import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Fachlogik für Prüfungsleistungen
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
public interface PruefungsleistungStrategie {

	/**
	 * @param leistung
	 * @return true, falls die übergebene Prüfungsleistung momentan editiert
	 *         werden kann
	 */
	boolean isPruefungsleistungEditable(Pruefungsleistung leistung);

	/**
	 * @param leistung
	 * @return die aktuell gültige Note der Prüfungsleistung (ist für
	 *         Ergänzungsprüfungen relevant)
	 */
	Note getAktuelleNote(Pruefungsleistung leistung);

	/**
	 * ermittelt anhand des letzten Versuches eines Studenten für ein
	 * Prüfungsfach ob bei der übergebenen Prüfung ein Versuch abgelegt werden
	 * kann
	 * 
	 * @param letzterVersuch
	 * @param pruefung
	 * @return Optional.absent() falls der Versuch zulässig ist, sonst den Grund
	 *         warum er es nicht ist
	 */
	Optional<String> pruefeVersuchZulaessig(Pruefungsleistung letzterVersuch, Pruefung pruefung);

	/**
	 * ermittelt für eine Prüfungsleistung, ob sie zum jetztigen Zeitpunkt als
	 * bestanden zu werten ist
	 * 
	 * @param leistung
	 * @return bestanden?
	 */
	boolean isBestanden(Pruefungsleistung leistung);

}
