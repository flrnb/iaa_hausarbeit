package de.nak.iaa.web.view.formbean;

import java.util.List;

import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;

public class PruefungsleistungAendernFormBean extends AbstractFormBean {

	private List<Pruefungsleistung> pruefungsleistungen;

	public PruefungsleistungAendernFormBean() {
	}

	public PruefungsleistungAendernFormBean(Student student,
			List<Pruefungsleistung> pruefungsleistungen) {
		this.student = student;
		this.pruefungsleistungen = pruefungsleistungen;

	}

	public List<Pruefungsleistung> getPruefungsleistungen() {
		return pruefungsleistungen;
	}

	public void setPruefungsleistungen(
			List<Pruefungsleistung> pruefungsleistungen) {
		this.pruefungsleistungen = pruefungsleistungen;
	}

}
