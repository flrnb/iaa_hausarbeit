package de.nak.iaa.server.entity;

public enum Studienrichtung {
	BWL("B"), WInf("I"), WIng("W");

	private String kuerzel;

	Studienrichtung(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public String getKuerzel() {
		return kuerzel;
	}

}
