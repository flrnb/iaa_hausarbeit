package de.nak.iaa.server.fachwert;

public enum Note {
	Eins(1.0), EinsDrei(1.3), EinsSieben(1.7), Zwei(2.0), ZweiDrei(2.3), ZweiSieben(
			2.7), Drei(3.0), DreiDrei(3.3), DreiSieben(3.7), Vier(4.0), Fuenf(
			5.0), Sechs(6.0);

	private Double note;

	Note(Double note) {
		this.note = note;
	}

	public Double getNote() {
		return note;
	}
}
