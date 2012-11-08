package de.nak.iaa.server.fachwert;

import java.util.Arrays;
import java.util.List;

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

	public static Note get(String note) {
		List<Note> noten = Arrays.asList(Note.values());
		for (Note noteEnum : noten) {
			if (noteEnum.getNote().equals(Double.valueOf(note))) {
				return noteEnum;
			}
		}
		return null;
	}

	Note(Double note) {
		this.note = note;
	}

	public static Note getNote(String note) {
		if (note.matches("[123456][.][037]") || note.contains("."))
			return get(note);
		else
			return get(note + ".0");
	}

	public static boolean isValid(String note) {
		return (getNote(note) == null) ? false : true;
	}

	public Double getNote() {
		return note;
	}

	@Override
	public String toString() {
		return getNote().toString();
	}
}
