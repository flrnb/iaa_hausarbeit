package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

import de.nak.iaa.server.business.IllegalUpdateException.IllegalPruefungsleistungException;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.entity.Protokollzeile;
import de.nak.iaa.web.view.formbean.PruefungsleistungFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenEintragenAction extends AbstractFormAction implements SessionAware, ParameterAware,
		Preparable, ValidationAware {

	private List<PruefungsleistungFormBean> pruefungenBeans;

	private List<Protokollzeile> protokoll;
	private boolean protokollHasErrors;

	/* Custom Logik Start */

	/**
	 * Fülle die pruefungenBeans
	 */
	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungFormBean>());

		for (Entry<Student, Optional<Pruefungsleistung>> student : getPruefungService().getAllStudentenForPruefung(
				getSelectedPruefung()).entrySet()) {
			if (student == null || !student.getKey().getManipel().equals(getSelectedManipel()))
				continue;
			else {
				// Optional<Note> alteNote =
				// getPruefungService().getAktuelleNote(
				// student, getSelectedPruefungsfach());

				Note alteNote = null;
				if (student.getValue().isPresent()) {
					alteNote = student.getValue().get().getNote();
				}

				getPruefungenBeans().add(new PruefungsleistungFormBean(student.getKey(), alteNote, null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/* Custom Logik Ende */
	/* Logik Start */

	@Override
	public void validate() {
	}

	/* Logik Ende */
	/* Actions Start */

	public String save() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		int i = 0;
		for (PruefungsleistungFormBean p : pruefungenBeans) {
			if (p.getNote().contains(",")) {
				addFieldError("pruefungenBeans[" + i + "].note", "Nur \".\" erlaubt");
				// continue;
			}
			if (!p.getNote().equals("") && !p.getNote().contains(",") && !Note.isValid(p.getNote())) {
				addFieldError("pruefungenBeans[" + i + "].note", "Keine gültige Note");
			}
			i++;
		}

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			protokollHasErrors = false;
			setProtokoll(new ArrayList<Protokollzeile>());

			for (PruefungsleistungFormBean p : pruefungenBeans) {
				if (p.getNote().equals(""))
					continue;

				Student st = null;
				for (Student s : getStudentService().getAllStudenten(getSelectedManipel())) {
					if (s.equals(p.getStudent())) {
						st = s;
						break;
					}

				}
				try {
					getPruefungService().addPruefungsleistung(getSelectedPruefung(), st, Note.getNote(p.getNote()));
					getProtokoll().add(
							new Protokollzeile(Protokollzeile.NACHRICHT, "Note " + p.getNote() + " für "
									+ st.getVorname() + " " + st.getName() + " erfolgreich eingetragen."));

				} catch (IllegalPruefungsleistungException e) {
					protokollHasErrors = true;
					getProtokoll().add(
							new Protokollzeile(Protokollzeile.FEHLER, "Note " + p.getNote() + " für " + st.getVorname()
									+ " " + st.getName() + " konnte nicht eingetragen werden (Grund: " + e.getMessage()
									+ ")."));
				}
			}

			return Action.SUCCESS;
		}
	}

	// TODO fehlerbehandlung hinzufügen
	public String show() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<PruefungsleistungFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(List<PruefungsleistungFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}

	public List<Protokollzeile> getProtokoll() {
		return protokoll;
	}

	public void setProtokoll(List<Protokollzeile> protokoll) {
		this.protokoll = protokoll;
	}

	public boolean isProtokollHasErrors() {
		return protokollHasErrors;
	}

	public void setProtokollHasErrors(boolean protokollHasErrors) {
		this.protokollHasErrors = protokollHasErrors;
	}
}
