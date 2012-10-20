package de.nak.iaa.server.business;

import java.util.List;

import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Service für pruefungs- und pruefungsleistungsbezogene Aufgaben
 * 
 * @author flrnb
 */
public interface PruefungService {

	List<Pruefungsfach> getAllPruefungsfaecher();

	void updatePruefungsleistung(Pruefungsleistung leistung, Note note);

}
