package de.nak.iaa.server.business;

import java.util.List;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;

public interface PruefungService {

	/**
	 * Soll null zurückgeben, wenn der Student keine Prüfung mehr schreiben
	 * muss. Gründe(mind): - ist schon dreimal durchgefallen - hat schon
	 * bestanden
	 * 
	 * Liste darf max. drei und mind. eine prüfungsleistungen enthalten (3x
	 * schriftl.)
	 * 
	 * Die letzte hinzugefügte prüfung (zu der liste) muss diejenige sein, die
	 * eingetragen werden soll
	 * 
	 * 
	 * @param student
	 * @param selectedPruefungsfach
	 * @return
	 */
	public List<Pruefungsleistung> getPruefungsleistungenForStudentAndPruefungsfach(
			Student student, Pruefungsfach selectedPruefungsfach);

	public List<Pruefungsfach> getPruefungsfaecherForManipel(Manipel manipel);

	public Pruefungsfach getPruefungsfachById(Long id);
}
