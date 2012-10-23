package de.nak.iaa.server.dao;

import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;

public interface PruefungsfachDAO extends GenericDAO<Pruefungsfach, Long> {

	List<Pruefungsfach> findByManipel(Manipel manipel);

}
