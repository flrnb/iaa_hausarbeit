package de.nak.iaa.server.business;

import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Fachlogik für Ergänzungsprüfungen
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
public interface ErgaenzungspruefungStrategie {

	boolean isErgaenzungsPruefungZulaessig(Pruefungsleistung pruefungsleistung);

	Note getNoteErgaenzungspruefung(int prozent);

}
