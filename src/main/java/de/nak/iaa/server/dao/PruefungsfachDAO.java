package de.nak.iaa.server.dao;

import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;

/**
 * Interface für ein DAO, welches sich um {@link Pruefungsfach} kümmert.
 * 
 * @author Ronny Bräunlich
 * 
 */
public interface PruefungsfachDAO extends GenericDAO<Pruefungsfach, Long> {

	List<Pruefungsfach> findePruefungsfaecherFuerManipel(Manipel manipel);

}
