package de.nak.iaa.server.fachwert;

public enum Studienrichtung {
	BWL('B'), WInf('I'), WIng('W');

	private Character kuerzel;

	Studienrichtung(Character kuerzel) {
		this.kuerzel = kuerzel;
	}

	public Character getKuerzel() {
		return kuerzel;
	}

}
