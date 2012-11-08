package de.nak.iaa.server.fachwert;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration für Noten. Alle Werte besitzen auch die entsprechende Zahl als
 * Double-Wert.
 * 
 * @author Ronny Bräunlich
 * 
 */
public enum Note {
	Eins(1.0), EinsDrei(1.3), EinsSieben(1.7), Zwei(2.0), ZweiDrei(2.3), ZweiSieben(
			2.7), Drei(3.0), DreiDrei(3.3), DreiSieben(3.7), Vier(4.0), Fuenf(
			5.0), Sechs(6.0);

	private Double note;
	private static final Map<Double, Note> lookup = new HashMap<Double, Note>();

	static {
		for (Note n : Note.values())
			lookup.put(n.getNote(), n);
	}

	public static Note get(String note) {
		return lookup.get(Double.valueOf(note));
	}

	Note(Double note) {
		this.note = note;
	}

	/**
	 * Hole eine Note zu einem String
	 * 
	 * @author Christopher Biel <christopher.biel89@gmail.com>
	 * @param note
	 * @return
	 */
	public static Note getNote(String note) {
		if (note.matches("[123456][.][037]") || note.contains("."))
			return lookup.get(Double.valueOf(note));
		else
			return lookup.get(Double.valueOf(note + ".0"));
	}

	/**
	 * Prüfe of eine Note valide ist
	 * 
	 * @author Christopher Biel <christopher.biel89@gmail.com>
	 * @param note
	 * @return
	 */
	public static boolean isValid(String note) {
		return (Note.getNote(note) == null) ? false : true;
	}

	public Double getNote() {
		return note;
	}

	@Override
	public String toString() {
		return getNote().toString();
	}
}
