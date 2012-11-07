package de.nak.iaa.server.dao;

import java.util.List;

import de.nak.iaa.server.dao.impl.PruefungsleistungDAOImpl.AlteRevisionPruefungsleistungContainer;
import de.nak.iaa.server.entity.ErgaenzungsPruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * Interface für ein DAO, welches sich um {@link Pruefungsleistung} kümmert.
 * Hier werden auch alle Methoden angeboten um anfragen an versionierte Daten zu
 * machen. Außerdem ist dieser DAO (bzw. die Pruefungsleistung) der einzige Weg
 * an {@link ErgaenzungsPruefung} zu kommen.
 * 
 * @author Ronny Bräunlich
 * 
 */
public interface PruefungsleistungDAO extends
		GenericDAO<Pruefungsleistung, Long> {

	Pruefungsleistung getAlteRevision(int revision, Long primaryKey);

	List<Pruefungsleistung> getVersuchFallsVorhanden(Student student,
			Pruefungsfach pruefungsfach, Versuch versuch);

	List<AlteRevisionPruefungsleistungContainer> getAltePruefungsleistungen(
			Student student, Pruefungsfach fach, Versuch versuch);
}
