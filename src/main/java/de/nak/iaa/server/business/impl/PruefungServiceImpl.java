package de.nak.iaa.server.business.impl;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Pruefungsfach;

/**
 * Implementierung von {@link PruefungService}
 * 
 * @author flrnb
 */
public class PruefungServiceImpl implements PruefungService {

	private PruefungsfachDAO pruefungsfachDAO;

	@Override
	public List<Pruefungsfach> getAllPruefungsfaecher() {
		return ImmutableList.copyOf(pruefungsfachDAO.findAll());
	}

	public void setPruefungsfachDAO(PruefungsfachDAO pruefungsfachDAO) {
		this.pruefungsfachDAO = pruefungsfachDAO;
	}

}
