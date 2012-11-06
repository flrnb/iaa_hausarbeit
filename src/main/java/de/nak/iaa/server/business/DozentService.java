package de.nak.iaa.server.business;

import java.util.List;

import com.google.common.base.Optional;

import de.nak.iaa.server.entity.Dozent;

/**
 * Service für Dozentenbezogene Funktionen
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
public interface DozentService {

	List<Dozent> getAllDozenten();

	Optional<Dozent> getDozentById(Long id);

}
