package de.nak.iaa.server.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.dao.PruefungsfachDAO;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Implementierung von {@link PruefungService}
 * 
 * @author flrnb
 */
public class PruefungServiceImpl implements PruefungService {

	@Autowired
	private PruefungsfachDAO pruefungsfachDAO;

	@Override
	public List<Pruefungsfach> getAllPruefungsfaecher() {
		return ImmutableList.copyOf(pruefungsfachDAO.findAll());
	}

	public void setPruefungsfachDAO(PruefungsfachDAO pruefungsfachDAO) {
		this.pruefungsfachDAO = pruefungsfachDAO;
	}

	@Override
	public void updatePruefungsleistung(Pruefungsleistung leistung, Note note) {
		// TODO Auto-generated method stub

	}

}
