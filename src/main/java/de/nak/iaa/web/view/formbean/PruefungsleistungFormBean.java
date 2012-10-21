package de.nak.iaa.web.view.formbean;

public class PruefungsleistungFormBean {

	private Long examId;
	private int matrikelNummer;
	private String name;
	private String alteNoten;
	private String note;

	public PruefungsleistungFormBean(Long id, int matrikelNr, String name,
			String alteNoten, String note) {
		this.examId = id;
		this.matrikelNummer = matrikelNr;
		this.name = name;
		this.alteNoten = alteNoten;
		this.note = note;
	}

	public int getMatrikelNummer() {
		return matrikelNummer;
	}

	public void setMatrikelNummer(int matrikelNummer) {
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

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
