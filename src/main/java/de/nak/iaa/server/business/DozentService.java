package de.nak.iaa.server.business;

import java.util.List;

import com.google.common.base.Optional;

import de.nak.iaa.server.entity.Dozent;

/**
 * Service für Dozentenbezogene Funktionen
 * 
 * @author flrnb
 */
public interface DozentService {

	List<Dozent> getAllDozenten();

	Optional<Dozent> getDozentById(Long id);

}
