package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;

public class PruefungsleistungAendernFormBean extends AbstractFormBean {

	private Note[] noten;
	private boolean[] writeable;

	public PruefungsleistungAendernFormBean() {
	}

	public PruefungsleistungAendernFormBean(Student student, Note[] noten,
			boolean[] writeable) {
		this.student = student;
		this.noten = noten;
		this.writeable = writeable;

	}

	public String getNote1() {
		return noten[0].toString();
	}

	public void setNote1(String note1) {
		this.noten[0] = Note.get(note1);
	}

	public String getNote2() {
		return noten[1].toString();
	}

	public void setNote2(String note2) {
		this.noten[1] = Note.get(note2);
	}

	public String getNote3() {
		return noten[2].toString();
	}

	public void setNote3(String note3) {
		this.noten[2] = Note.get(note3);
	}

	public boolean[] getWriteable() {
		return writeable;
	}

	public void setWriteable(boolean[] writeable) {
		this.writeable = writeable;
	}

}
