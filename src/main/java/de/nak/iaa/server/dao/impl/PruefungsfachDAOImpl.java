package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Pruefungsfach;

@Repository
public class PruefungsfachDAOImpl extends
		GenericHibernateDAOImpl<Pruefungsfach, Long> implements
		PruefungsfachDAO {

}
