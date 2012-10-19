package de.nak.iaa.server.entity;

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
