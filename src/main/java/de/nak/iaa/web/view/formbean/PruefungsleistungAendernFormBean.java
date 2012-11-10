package de.nak.iaa.web.view.formbean;

import java.util.List;

import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;

/**
 * FormBean für das Ändern von Prüfungsleistungen<br>
 * Stellt Daten für das Formular bereit und hilft bei der Auswertung
 * 
 * @author Christopher Biel
 */
public class PruefungsleistungAendernFormBean extends AbstractFormBean {

	private List<Pruefungsleistung> pruefungsleistungen;

	private boolean isDelete = false;

	public PruefungsleistungAendernFormBean() {
	}

	public PruefungsleistungAendernFormBean(Student student, List<Pruefungsleistung> pruefungsleistungen) {
		this.student = student;
		this.pruefungsleistungen = pruefungsleistungen;

	}

	public List<Pruefungsleistung> getPruefungsleistungen() {
		return pruefungsleistungen;
	}

	public void setPruefungsleistungen(List<Pruefungsleistung> pruefungsleistungen) {
		this.pruefungsleistungen = pruefungsleistungen;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

}
