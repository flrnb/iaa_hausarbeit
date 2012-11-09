package de.nak.iaa.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

import de.nak.iaa.server.business.PruefungsleistungHistoryEntry;
import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * Implementierung von {@link PruefungsleistungDAO}. Diese Klasse benutzt Envers
 * direkt.
 * 
 * @author Ronny Bräunlich
 * 
 */
@Repository
public class PruefungsleistungDAOImpl extends
		GenericHibernateDAOImpl<Pruefungsleistung, Long> implements
		PruefungsleistungDAO {

	@Override
	public Pruefungsleistung getAlteRevision(int revision, Long primaryKey) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		return auditReader.find(Pruefungsleistung.class, primaryKey, revision);
	}

	/**
	 * Sucht für den Studenten, das Prüfungsfach und den Versuch die
	 * Prüfungsleistungen raus, falls vorhanden. Falls nicht, wird eine leere
	 * Liste zurück geliefert.
	 */
	@Override
	public List<Pruefungsleistung> getVersuchFallsVorhanden(Student student,
			Pruefungsfach pruefungsfach, Versuch versuch) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		AuditQuery query = auditReader.createQuery()
				.forRevisionsOfEntity(Pruefungsleistung.class, true, true)
				.add(AuditEntity.property("versuch").eq(versuch))
				.add(AuditEntity.property("student").eq(student));
		@SuppressWarnings("unchecked")
		List<Pruefungsleistung> resultList = query.getResultList();
		// Pruefungsfach ist leider nicht direkt mit der Versionierung
		// assoziiert
		return filterePruefungsleistungNachPruefungsfach(resultList,
				pruefungsfach);
	}

	/**
	 * Filtert die Prüfungsleistungen nach dem entsprechenden Prüfungsfach.
	 * 
	 * @param resultList
	 * @param pruefungsfach
	 * @return
	 */
	private List<Pruefungsleistung> filterePruefungsleistungNachPruefungsfach(
			List<Pruefungsleistung> resultList,
			final Pruefungsfach pruefungsfach) {
		return ImmutableList.copyOf(Collections2.filter(resultList,
				new Predicate<Pruefungsleistung>() {
					@Override
					public boolean apply(Pruefungsleistung p) {
						return p.getPruefung().getPruefungsfach()
								.equals(pruefungsfach);
					}
				}));
	}

	@Override
	public List<PruefungsleistungHistoryEntry> getAltePruefungsleistungen(
			Student student, Pruefungsfach fach, Versuch versuch) {
		AuditReader auditReader = AuditReaderFactory.get(getSession());
		AuditQuery query = auditReader.createQuery()
				.forRevisionsOfEntity(Pruefungsleistung.class, false, true)
				.add(AuditEntity.property("student").eq(student))
				.add(AuditEntity.property("versuch").eq(versuch));
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.getResultList();
		ArrayList<PruefungsleistungHistoryEntry> ergebnis = new ArrayList<PruefungsleistungHistoryEntry>();
		for (Object[] objects : result) {
			Pruefungsleistung pl = (Pruefungsleistung) objects[0];
			// Workaround!!! https://hibernate.onjira.com/browse/HHH-3552
			if (pl.getErgaenzungsPruefung() != null)
				pl.getErgaenzungsPruefung().getId();
			DefaultRevisionEntity revEntity = (DefaultRevisionEntity) objects[1];
			RevisionType revType = (RevisionType) objects[2];
			if (pl.getPruefung().getPruefungsfach().equals(fach)) {
				ergebnis.add(new PruefungsleistungHistoryEntry(pl, revEntity
						.getRevisionDate(), revType.equals(RevisionType.DEL)));
			}
		}
		return ergebnis;
	}
}
