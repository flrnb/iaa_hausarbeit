package de.nak.iaa.server.dao;

import de.nak.iaa.server.entity.DummyEntity;

/**
 * Plain DAO which provides only {@link de.nak.iaa.server.dao.impl.GenericHibernateDAOImpl} methods
 */
public interface DummyDAO extends GenericDAO<DummyEntity, Long> {
}
