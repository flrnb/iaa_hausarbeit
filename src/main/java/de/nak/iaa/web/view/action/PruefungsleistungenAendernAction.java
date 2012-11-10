package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.business.IllegalUpdateException;
import de.nak.iaa.server.business.PruefungsleistungAenderung;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.view.formbean.PruefungsleistungAendernFormBean;

/**
 * Action zum Ändern und Löschen von Prüfungsleistungen
 * 
 * @author Christopher Biel
 * 
 */
public class PruefungsleistungenAendernAction extends AbstractFormAction {

	private static final long serialVersionUID = 1L;

	private List<PruefungsleistungAendernFormBean> pruefungenBeans;

	/* Custom Logik Start */

	/**
	 * Fülle die pruefungenBeans
	 */
	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungAendernFormBean>());

		for (Student student : getStudentService().getAllStudenten(getSelectedManipel())) {
			if (student == null)
				continue;
			else {
				List<Pruefungsleistung> leistungen = getPruefungService().getAllPruefungsleistungen(
						getSelectedPruefungsfach(), student);

				if (!leistungen.isEmpty())
					getPruefungenBeans().add(new PruefungsleistungAendernFormBean(student, leistungen));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/**
	 * Hilfsmethode für die View (jsp) um zu prüfen, ob eine Prüfungsleistung editierbar ist
	 * 
	 * @param id
	 * @return
	 */
	public boolean isWriteable(Long id) {
		return getPruefungService().isPruefungsleistungEditable(id);
	}

	/**
	 * Hilfsmethode für die View (jsp) um zu prüfen, ob eine Prüfungsleistung eine Ergänzungsprüfung
	 * hat<br>
	 * Gib in dem Fall den auszugebenen String zurück
	 * 
	 * @param leistung
	 * @return
	 */
	public String pruefungsleistungHasErgaenzungspruefung(Pruefungsleistung leistung) {
		return (leistung.getErgaenzungsPruefung() != null) ? " > " + leistung.getErgaenzungsPruefung().getNote() : "";
	}

	/**
	 * Validiere die Eingabedaten für die Zeilen, wo mindestens eines der Felder gefüllt ist<br>
	 * Fügt bei einem Problem, dem Feld einen FieldError hinzu
	 */
	private void validateForm() {
		int i = 0;
		for (PruefungsleistungAendernFormBean p : pruefungenBeans) {
			int k = 0;
			for (Pruefungsleistung pl : p.getPruefungsleistungen()) {
				if (pl.getNote() == null)
					continue;
				if ((isWriteable(pl.getId()) && !Note.isValid(String.valueOf(pl.getNote().getNote())))) {
					addFieldError("pruefungenBeans[" + i + "].pruefungsleistungen[" + k + "].note",
							"Keine gültige Note");
				}
				k++;
			}
			i++;
		}
	}

	/* Custom Logik Ende */
	/* Actions Start */

	/**
	 * Zeige das Formular
	 * 
	 * @return
	 */
	public String show() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/**
	 * Speichere die Eingabedaten nachdem sie validiert wurden
	 * 
	 * @return
	 */
	public String save() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}
		validateForm();

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			List<PruefungsleistungAenderung> aenderungen = new ArrayList<PruefungsleistungAenderung>();
			for (PruefungsleistungAendernFormBean p : pruefungenBeans) {
				for (Pruefungsleistung pl : p.getPruefungsleistungen()) {
					if (getPruefungService().isPruefungsleistungEditable(pl.getId())) {
						if (p.isDelete())
							aenderungen.add(new PruefungsleistungAenderung.Delete(pl.getId()));
						else
							aenderungen.add(new PruefungsleistungAenderung.Update(pl.getId(), pl.getNote()));
					}
				}
			}

			if (!aenderungen.isEmpty()) {
				try {
					getPruefungService().updatePruefungsleistungen(aenderungen);
				} catch (IllegalUpdateException e) {
					setOccuredErrorCode(3);
					return SPECIFIC_EXCEPTION_OCCURED;
				}
			}
			return Action.SUCCESS;
		}
	}

	/* Actions Ende */
	/* Properties */

	public List<PruefungsleistungAendernFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(List<PruefungsleistungAendernFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
