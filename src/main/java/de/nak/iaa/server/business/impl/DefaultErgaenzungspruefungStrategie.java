package de.nak.iaa.server.business.impl;

import de.nak.iaa.server.business.ErgaenzungspruefungStrategie;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Standardimplementierung für {@link ErgaenzungspruefungStrategie}
 * 
 * @author flrnb
 */
public class DefaultErgaenzungspruefungStrategie implements ErgaenzungspruefungStrategie {

	@Override
	public boolean isErgaenzungsPruefungZulaessig(final Pruefungsleistung pruefungsleistung) {
		return pruefungsleistung.getNote().equals(Note.Fuenf) && pruefungsleistung.getErgaenzungsPruefung() == null;
	}

	@Override
	public Note getNoteErgaenzungspruefung(int prozent) {
		return (prozent >= 80) ? Note.Vier : Note.Fuenf;
	}
}
