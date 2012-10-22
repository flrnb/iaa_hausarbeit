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

	public Optional<Versuch> next() {
		switch (this) {
		case Eins:
			return Optional.of(Zwei);
		case Zwei:
			return Optional.of(Drei);
		default:
			return Optional.absent();
		}
	}
}
