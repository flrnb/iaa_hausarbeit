package de.nak.iaa.web.entity;

public class Protokollzeile {

	private Protokolltyp typ;
	private String nachricht;

	public Protokollzeile(Protokolltyp typ, String nachricht) {
		super();
		this.typ = typ;
		this.nachricht = nachricht;
	}

	public String getNachricht() {
		return nachricht;
	}

	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	public Protokolltyp getTyp() {
		return typ;
	}

	public void setTyp(Protokolltyp typ) {
		this.typ = typ;
	}

}
