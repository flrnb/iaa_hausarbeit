package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.entity.Student;

public class PruefungsleistungAendernFormBean extends AbstractFormBean {

	private String note1;
	private String note2;
	private String note3;

	public PruefungsleistungAendernFormBean() {
	}

	public PruefungsleistungAendernFormBean(Student student, String note1,
			String note2, String note3) {
		this.student = student;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getNote3() {
		return note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

}
