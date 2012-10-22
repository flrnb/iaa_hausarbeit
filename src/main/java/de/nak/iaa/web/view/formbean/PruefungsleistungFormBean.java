package de.nak.iaa.web.view.formbean;

import com.google.common.base.Optional;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;

public class PruefungsleistungFormBean extends AbstractFormBean {

	private Optional<Note> alteNote;
	private String note;

	public PruefungsleistungFormBean() {
	}

	public PruefungsleistungFormBean(Student student, Optional<Note> alteNote2,
			String note) {
		this.student = student;
		this.alteNote = alteNote2;
		this.note = note;
	}

	public Optional<Note> getAlteNote() {
		return alteNote;
	}

	public void setAlteNote(Optional<Note> alteNote) {
		this.alteNote = alteNote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
