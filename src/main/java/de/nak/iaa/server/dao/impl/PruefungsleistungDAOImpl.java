package de.nak.iaa.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Versuch;

@Repository
public class PruefungsleistungDAOImpl extends
		GenericHibernateDAOImpl<Pruefungsleistung, Long> implements
		PruefungsleistungDAO {

	@Override
	public Pruefungsleistung getAlteRevision(int revision, Long primaryKey) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		return auditReader.find(Pruefungsleistung.class, primaryKey, revision);
	}

	@Override
	@Deprecated
	public List<Pruefungsleistung> getAlteRevisionenFuerVersuch(
			Long primaryKey, Versuch versuch) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		AuditQuery query = auditReader.createQuery()
				.forRevisionsOfEntity(Pruefungsleistung.class, true, true)
				.add(AuditEntity.property("versuch").eq(versuch))
				.add(AuditEntity.id().eq(primaryKey));
		return query.getResultList();
	}

	@Override
	public Pruefungsleistung getNachfolgendenVersuchFallsVorhanden(
			Student student, Pruefungsfach pruefungsfach, Versuch versuch) {
		if (versuch.next().isPresent()) {
			AuditReader auditReader = AuditReaderFactory.get(getSession());
			AuditQuery query = auditReader
					.createQuery()
					.forRevisionsOfEntity(Pruefungsleistung.class, true, true)
					.add(AuditEntity.property("versuch").eq(
							versuch.next().get()))
					.add(AuditEntity.property("student").eq(student));
			@SuppressWarnings("unchecked")
			List<Pruefungsleistung> resultList = query.getResultList();
			// Pruefungsfach ist leider nicht direkt mit der Versionierung
			// assoziiert
			return filterePruefungsleistungNachPruefungsfach(resultList,
					pruefungsfach);
		} else
			throw new IllegalArgumentException(
					"Es kann keinen Nachfolgeversuch für einen 3. Versuch geben");

	}

	private Pruefungsleistung filterePruefungsleistungNachPruefungsfach(
			List<Pruefungsleistung> resultList, Pruefungsfach pruefungsfach) {
		for (Pruefungsleistung pruefungsleistung : resultList) {
			if (pruefungsleistung.getPruefung().getPruefungsfach()
					.equals(pruefungsfach))
				return pruefungsleistung;
		}
		return null;
	}

	// http://docs.jboss.org/envers/docs/index.html#revisionlog
	public Map<Pruefungsleistung, Date> getAltePruefungsleistungen(Long id) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		AuditQuery query = auditReader.createQuery()
				.forRevisionsOfEntity(Pruefungsleistung.class, false, true)
				.add(AuditEntity.id().eq(id));
		/*
		 * siehe Methodenkommentar
		 * org.hibernate.envers.query.AuditQueryCreator.forRevisionsOfEntity
		 * (Class<?>, boolean, boolean) bzgl. des Object Arrays usw.
		 */
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.getResultList();
		Map<Pruefungsleistung, Date> ergebnis = new HashMap<Pruefungsleistung, Date>();
		for (Object[] objects : result) {
			Pruefungsleistung pl = (Pruefungsleistung) objects[0];
			DefaultRevisionEntity revEntity = (DefaultRevisionEntity) objects[1];
			ergebnis.put(pl, new Date(revEntity.getTimestamp()));
		}
		return ergebnis;

	}
}
