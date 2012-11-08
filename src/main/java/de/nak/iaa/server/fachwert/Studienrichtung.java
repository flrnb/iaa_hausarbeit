package de.nak.iaa.server.fachwert;

/**
 * Enumaration für die Studienrichtungen. Jede Studienrichtung kennt ihre eigene
 * Abkürzung.
 * 
 * @author Ronny Bräunlich
 * 
 */
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
