package de.nak.iaa.server.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

import de.nak.iaa.server.business.DozentService;
import de.nak.iaa.server.dao.DozentDAO;
import de.nak.iaa.server.entity.Dozent;

public class DozentServiceImpl implements DozentService {

	@Autowired
	private DozentDAO dozentDAO;

	@Override
	public List<Dozent> getAllDozenten() {
		return dozentDAO.findAll();
	}

	@Override
	public Optional<Dozent> getDozentById(Long id) {
		return Optional.fromNullable(dozentDAO.findById(id, false));
	}

	public void setDozentDAO(DozentDAO dozentDAO) {
		this.dozentDAO = dozentDAO;
	}

}
