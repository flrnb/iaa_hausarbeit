package de.nak.iaa.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;

@Repository
public class PruefungsfachDAOImpl extends
		GenericHibernateDAOImpl<Pruefungsfach, Long> implements
		PruefungsfachDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Pruefungsfach> findePruefungsfaecherFuerManipel(Manipel manipel) {
		Query query = getSession()
				.createQuery(
						"SELECT DISTINCT p FROM Pruefungsfach p WHERE p.manipel = :manipel");
		query.setParameter("manipel", manipel);
		return query.list();
	}

}
