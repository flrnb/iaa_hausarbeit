package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.DozentDAO;
import de.nak.iaa.server.entity.Dozent;

/**
 * 
 * @author Ibrahim Karagac
 * 
 */

@Repository
public class DozentDAOImpl extends GenericHibernateDAOImpl<Dozent, Long>
		implements DozentDAO {

}
