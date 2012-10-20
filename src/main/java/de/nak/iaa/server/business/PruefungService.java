package de.nak.iaa.server.business;

import java.util.List;

import de.nak.iaa.server.entity.Pruefungsfach;

/**
 * Service für pruefungs- und pruefungsleistungsbezogene Aufgaben
 * 
 * @author flrnb
 */
public interface PruefungService {

	List<Pruefungsfach> getAllPruefungsfaecher();

}
