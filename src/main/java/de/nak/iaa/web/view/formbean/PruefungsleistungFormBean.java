package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;

/**
 * FormBean für das Formular zum Erfassen neuer Prüfungsleistungen<br>
 * Stellt Daten für das Formular bereit und hilft bei der Auswertung
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
public class PruefungsleistungFormBean extends AbstractFormBean {

	private Note alteNote;
	private String note;

	public PruefungsleistungFormBean() {
	}

	public PruefungsleistungFormBean(Student student, Note alteNote, String note) {
		this.student = student;
		this.alteNote = alteNote;
		this.note = note;
	}

	public Note getAlteNote() {
		return alteNote;
	}

	public void setAlteNote(Note alteNote) {
		this.alteNote = alteNote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
