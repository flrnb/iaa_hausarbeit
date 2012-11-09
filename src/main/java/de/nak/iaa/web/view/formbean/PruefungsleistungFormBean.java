package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.entity.Student;

/**
 * FormBean für das Formular zum Erfassen neuer Prüfungsleistungen<br>
 * Stellt Daten für das Formular bereit und hilft bei der Auswertung
 * 
 * @author Christopher Biel
 */
public class PruefungsleistungFormBean extends AbstractFormBean {

	private String alteNote;
	private String note;

	public PruefungsleistungFormBean() {
	}

	public PruefungsleistungFormBean(Student student, String alteNote, String note) {
		this.student = student;
		this.alteNote = alteNote;
		this.note = note;
	}

	public String getAlteNote() {
		return alteNote;
	}

	public void setAlteNote(String alteNote) {
		this.alteNote = alteNote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
