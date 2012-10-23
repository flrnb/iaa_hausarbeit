package de.nak.iaa.web.entity;

public class Protokollzeile {

	public final static String FEHLER = "fehler";
	public final static String NACHRICHT = "nachricht";

	private String typ;
	private String nachricht;

	public Protokollzeile(String typ, String nachricht) {
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

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

}
