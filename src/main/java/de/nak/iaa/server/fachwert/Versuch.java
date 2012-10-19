package de.nak.iaa.server.fachwert;

import com.google.common.base.Optional;

public enum Versuch {
	Eins(1), Zwei(2), Drei(3);

	private int intDarstellung;

	Versuch(int intDarstellung) {
		this.intDarstellung = intDarstellung;
	}

	public int toInt() {
		return intDarstellung;
	}

	public Optional<Integer> next() {
		if (this.equals(Drei))
			return Optional.absent();
		return Optional.of(intDarstellung + 1);
	}
}
