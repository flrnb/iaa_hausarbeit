package de.nak.iaa.server.business.impl;

import com.google.common.base.Optional;

import de.nak.iaa.server.business.PruefungsleistungStrategie;
import de.nak.iaa.server.entity.ErgaenzungsPruefung;
import de.nak.iaa.server.entity.Pruefung;
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
	public Optional<String> pruefeVersuchZulaessig(Pruefungsleistung letzterVersuch, Pruefung pruefung) {
		Versuch versuch = letzterVersuch.getVersuch();
		if (isBestanden(letzterVersuch) || !versuch.next().isPresent())
			return Optional.of(ZU_OFT);
		if (letzterVersuch.getPruefung().equals(pruefung))
			return Optional.of(SCHON_VORHANDEN);
		if (pruefung.getDatum().before(letzterVersuch.getPruefung().getDatum()))
			return Optional.of(PRUEFUNG_ZUKUNFT);
		return Optional.absent();
	}

	@Override
	public Note getAktuelleNote(Pruefungsleistung leistung) {
		ErgaenzungsPruefung ergPruefung = leistung.getErgaenzungsPruefung();
		if (ergPruefung == null)
			return leistung.getNote();
		else
			return ergPruefung.getNote();
	}

	private static final String ZU_OFT = "pruefung.zuOft";

	private static final String PRUEFUNG_ZUKUNFT = "pruefung.zukunftVorhanden";

	private static final String SCHON_VORHANDEN = "pruefung.schonVorhanden";

}
