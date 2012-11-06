package de.nak.iaa.server.business;

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

	boolean isWeitererVersuchZulaessig(Pruefungsleistung leistung);

	boolean isBestanden(Pruefungsleistung leistung);

}
