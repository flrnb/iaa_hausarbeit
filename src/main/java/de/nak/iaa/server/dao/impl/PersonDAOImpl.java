package de.nak.iaa.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.nak.iaa.server.dao.PersonDAO;
import de.nak.iaa.server.entity.Person;

@Repository
public class PersonDAOImpl extends GenericHibernateDAOImpl<Person, Long>
		implements PersonDAO {

}
