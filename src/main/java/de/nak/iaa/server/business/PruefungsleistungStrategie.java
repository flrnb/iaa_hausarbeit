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

	boolean isPruefungsleistungEditable(Pruefungsleistung leistung);

	Note getAktuelleNote(Pruefungsleistung leistung);

	Optional<String> pruefeVersuchZulaessig(Pruefungsleistung letzterVersuch, Pruefung pruefung);

	boolean isBestanden(Pruefungsleistung leistung);

}
