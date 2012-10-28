package de.nak.iaa.server.business;

/**
 * Exception die geworfen wird, falls versucht wurde eine Prüfungsleistung
 * anzulegen, obwohl die fachlich nicht (mehr) zulässig ist.
 * 
 * @author flrnb
 */
public class IllegalPruefungsleistungException extends Exception {

	public IllegalPruefungsleistungException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -2554662398324014159L;

}
