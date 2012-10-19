package de.nak.iaa.web.view.formbean;

public class PruefungsleistungsBean {

	private String examId;
	private String matrikelNummer;
	private String name;
	private String alteNoten;
	private String note;

	public String getMatrikelNummer() {
		return matrikelNummer;
	}

	public void setMatrikelNummer(String matrikelNummer) {
		this.matrikelNummer = matrikelNummer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlteNoten() {
		return alteNoten;
	}

	public void setAlteNoten(String alteNoten) {
		this.alteNoten = alteNoten;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
