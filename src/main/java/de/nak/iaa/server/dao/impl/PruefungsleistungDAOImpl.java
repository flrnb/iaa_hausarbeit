package de.nak.iaa.server.dao.impl;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.entity.Pruefungsleistung;

@Repository
public class PruefungsleistungDAOImpl extends
		GenericHibernateDAOImpl<Pruefungsleistung, Long> implements
		PruefungsleistungDAO {

	@Override
	public Pruefungsleistung getOldRevision(int revision, Long primaryKey) {
		getSession().flush();
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		System.out.println(auditReader.getRevisions(Pruefungsleistung.class,
				primaryKey).isEmpty());
		return auditReader.find(Pruefungsleistung.class, primaryKey, revision);
	}
}
