package de.nak.iaa.server.business;

import de.nak.iaa.server.dao.PruefungsleistungDAO;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.fachwert.Note;

/**
 * Hier werden alle Änderungsoperationen an Prüfungen zusammengefasst.<br/>
 * <i>Orientiert sich am GOF-Command-Pattern.</i>
 * 
 * @author flrnb
 */
public interface PruefungsleistungAenderung {

	void perform(PruefungsleistungDAO dao);

	Long getId();

	/**
	 * Eine vorhandene Prüfungsleistung ändern
	 * 
	 * @author flrnb
	 */
	public static class Update implements PruefungsleistungAenderung {

		private final Long id;

		private final Note note;

		/**
		 * @param id
		 * @param note
		 * @require id ist gültig
		 */
		public Update(Long id, Note note) {
			this.id = id;
			this.note = note;
		}

		@Override
		public void perform(PruefungsleistungDAO dao) {
			Pruefungsleistung leistung = dao.findById(id, false);
			leistung.setNote(note);
			dao.makePersistent(leistung);
		}

		@Override
		public Long getId() {
			return id;
		}
	}

	/**
	 * Eine vorhandene Prüfungsleistung ändern
	 * 
	 * @author flrnb
	 */
	public static class Delete implements PruefungsleistungAenderung {

		private final Long id;

		/**
		 * @param id
		 * @require id ist gültig
		 */
		public Delete(Long id) {
			this.id = id;
		}

		@Override
		public void perform(PruefungsleistungDAO dao) {
			Pruefungsleistung toDelete = dao.findById(id, false);
			dao.makeTransient(toDelete);
		}

		@Override
		public Long getId() {
			return id;
		}
	}
}
