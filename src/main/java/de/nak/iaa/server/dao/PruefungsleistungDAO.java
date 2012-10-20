package de.nak.iaa.server.dao;

import de.nak.iaa.server.entity.Pruefungsleistung;

public interface PruefungsleistungDAO extends
		GenericDAO<Pruefungsleistung, Long> {

	Pruefungsleistung getOldRevision(int revision, Long primaryKey);

}
