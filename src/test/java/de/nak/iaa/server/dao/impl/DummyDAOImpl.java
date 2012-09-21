package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.DummyDAO;
import de.nak.iaa.server.entity.DummyEntity;

/**
 * Plain DAO which provides only {@link de.nak.iaa.server.dao.impl.GenericHibernateDAOImpl} methods
 */
@Repository
public class DummyDAOImpl extends GenericHibernateDAOImpl<DummyEntity, Long> implements DummyDAO {
    
}
