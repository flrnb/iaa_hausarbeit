package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.fachwert.Note;

public class PruefungsleistungFormBean extends AbstractFormBean {

	private Note alteNote;
	private String note;

	public PruefungsleistungFormBean() {
	}

	public PruefungsleistungFormBean(Integer matrikelNummer, String name,
			Note alteNote2, String note) {
		this.matrikelNummer = matrikelNummer;
		this.name = name;
		this.alteNote = alteNote2;
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
