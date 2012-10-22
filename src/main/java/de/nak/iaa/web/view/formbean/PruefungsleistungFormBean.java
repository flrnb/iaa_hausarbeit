package de.nak.iaa.web.view.formbean;

public class PruefungsleistungFormBean implements
		Comparable<PruefungsleistungFormBean> {

	private int matrikelNummer;
	private String name;
	private String alteNoten;
	private String note;

	public PruefungsleistungFormBean() {
	}

	public PruefungsleistungFormBean(int matrikelNr, String name,
			String alteNoten, String note) {
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int compareTo(PruefungsleistungFormBean o) {
		return this.getName().compareTo(o.getName());
	}
}
