package de.nak.iaa.server.business;

import java.util.List;

import de.nak.iaa.server.entity.Dozent;

/**
 * Service für Dozentenbezogene Funktionen
 * 
 * @author flrnb
 */
public interface DozentService {

	List<Dozent> getAllDozenten();

}
