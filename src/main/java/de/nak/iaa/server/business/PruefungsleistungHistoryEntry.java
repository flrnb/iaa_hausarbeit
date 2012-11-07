package de.nak.iaa.server.business;

import java.util.Date;

import de.nak.iaa.server.entity.Pruefungsleistung;

/**
 * @author Ronny Bräunlich
 * @version 07.11.2012
 * 
 *          Ein Eintrag aus der Historie einer Prüfungsleistung
 */
public class PruefungsleistungHistoryEntry {

	private final Pruefungsleistung pruefungsleistung;
	private final boolean isGeloescht;
	private final Date gueltigVon;

	public PruefungsleistungHistoryEntry(Pruefungsleistung pl, Date gueltigVon, boolean isGeloescht) {
		this.pruefungsleistung = pl;
		this.gueltigVon = gueltigVon;
		this.isGeloescht = isGeloescht;
	}

	public Pruefungsleistung getPruefungsleistung() {
		return pruefungsleistung;
	}

	/**
	 * @return true, falls die Prüfungsleistung storniert wurde
	 */
	public boolean isGeloescht() {
		return isGeloescht;
	}

	/**
	 * liefert das Änderungsdatum des aktuellen Eintrags, das heißt, eine
	 * Änderung an der Prüfungsleistung oder die Stornierung
	 * 
	 * @return Date
	 */
	public Date getGueltigVon() {
		return gueltigVon;
	}
}