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
public interface PruefungsAenderung {

	void perform(PruefungsleistungDAO dao) throws IllegalStateException;

	Long getId();

	public static class Update implements PruefungsAenderung {

		private final Long id;

		private final Note note;

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

	public static class Delete implements PruefungsAenderung {

		private final Long id;

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
