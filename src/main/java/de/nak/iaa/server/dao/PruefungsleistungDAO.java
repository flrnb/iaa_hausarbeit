package de.nak.iaa.server.dao;

import java.util.List;

import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Versuch;

public interface PruefungsleistungDAO extends
		GenericDAO<Pruefungsleistung, Long> {

	Pruefungsleistung getOldRevision(int revision, Long primaryKey);

	List<Pruefungsleistung> getAlteRevisionenFuerVersuch(Long primaryKey,
			Versuch versuch);
}
