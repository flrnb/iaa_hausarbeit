package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.entity.Manipel;

@Repository
public class ManipelDAOImpl extends GenericHibernateDAOImpl<Manipel, Long>
		implements ManipelDAO {

}
