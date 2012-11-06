package de.nak.iaa.server.business.impl;

import de.nak.iaa.server.business.PruefungsleistungStrategie;
import de.nak.iaa.server.entity.ErgaenzungsPruefung;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * Standardimplementierung für {@link PruefungsleistungStrategie}
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
public class DefaultPruefungsleistungStrategie implements PruefungsleistungStrategie {

	@Override
	public boolean isPruefungsleistungEditable(Pruefungsleistung leistung) {
		return leistung.getErgaenzungsPruefung() == null;
	}

	@Override
	public boolean isBestanden(Pruefungsleistung leistung) {
		switch (leistung.getNote()) {
		case Sechs:
			return false;
		case Fuenf:
			ErgaenzungsPruefung ergaenzungsPruefung = leistung.getErgaenzungsPruefung();
			if (ergaenzungsPruefung == null)
				return false;
			return ergaenzungsPruefung.getNote().getNote() <= Note.Vier.getNote();
		default:
			return true;
		}
	}

	@Override
	public boolean isWeitererVersuchZulaessig(Pruefungsleistung leistung) {
		Versuch versuch = leistung.getVersuch();
		return !isBestanden(leistung) && versuch.next().isPresent();
	}

	@Override
	public Note getAktuelleNote(Pruefungsleistung leistung) {
		ErgaenzungsPruefung ergPruefung = leistung.getErgaenzungsPruefung();
		if (ergPruefung == null)
			return leistung.getNote();
		else
			return ergPruefung.getNote();
	}

}
