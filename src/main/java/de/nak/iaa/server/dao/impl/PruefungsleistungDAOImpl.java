package de.nak.iaa.server.dao.impl;

import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Versuch;

@Repository
public class PruefungsleistungDAOImpl extends
		GenericHibernateDAOImpl<Pruefungsleistung, Long> implements
		PruefungsleistungDAO {

	@Override
	public Pruefungsleistung getOldRevision(int revision, Long primaryKey) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		return auditReader.find(Pruefungsleistung.class, primaryKey, revision);
	}

	@Override
	public List<Pruefungsleistung> getAlteRevisionenFuerVersuch(
			Long primaryKey, Versuch versuch) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		AuditQuery query = auditReader.createQuery()
				.forRevisionsOfEntity(Pruefungsleistung.class, true, true)
				.add(AuditEntity.property("versuch").eq(versuch))
				.add(AuditEntity.id().eq(primaryKey));
		return query.getResultList();
	}
}
