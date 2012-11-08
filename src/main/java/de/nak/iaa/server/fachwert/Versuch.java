package de.nak.iaa.server.fachwert;

import com.google.common.base.Optional;

/**
 * Enumeration für Versuche.
 * 
 * @author Ronny Bräunlich
 * 
 */
public enum Versuch {
	Eins(1), Zwei(2), Drei(3);

	private int intDarstellung;

	Versuch(int intDarstellung) {
		this.intDarstellung = intDarstellung;
	}

	public int toInt() {
		return intDarstellung;
	}

	/**
	 * Liefert den folgenden Versuch, falls vorhanden. Falls das Objekt schon
	 * der 3. Versuch ist, ist das Optional-Objekt leer.
	 * 
	 */
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
