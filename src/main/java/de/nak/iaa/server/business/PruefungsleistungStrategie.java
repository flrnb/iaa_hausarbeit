package de.nak.iaa.server.business;

import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Fachlogik für Prüfungsleistungen
 * 
 * @author flrnb
 */
public interface PruefungsleistungStrategie {

	boolean isPruefungsleistungEditable(Pruefungsleistung leistung, boolean hasFachlichenNachfolger);

	Note getAktuelleNote(Pruefungsleistung leistung);

	boolean isWeitererVersuchZulaessig(Pruefungsleistung leistung);

	boolean isBestanden(Pruefungsleistung leistung);

}
