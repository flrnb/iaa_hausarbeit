package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PruefungDAO;
import de.nak.iaa.server.entity.Pruefung;

@Repository
public class PruefungDAOImpl extends GenericHibernateDAOImpl<Pruefung, Long>
		implements PruefungDAO {

}
