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

	/**
	 * @param pruefungsleistung
	 * @return true, falls zu der übergebenen Prüfungsleistung eine
	 *         Ergänzungsprüfung zulässig ist
	 */
	boolean isErgaenzungsPruefungZulaessig(Pruefungsleistung pruefungsleistung);

	/**
	 * ermittelt für eine in der Ergänzungsprüfung erreichte Prozentzahl die zu
	 * vergebende Note
	 * 
	 * @param prozent
	 * @return Note
	 */
	Note getNoteErgaenzungspruefung(int prozent);

}
