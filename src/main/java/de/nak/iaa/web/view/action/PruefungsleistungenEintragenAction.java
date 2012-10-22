package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.view.formbean.PruefungsleistungFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenEintragenAction extends AbstractFormAction
		implements SessionAware, ParameterAware, Preparable, ValidationAware {

	private List<PruefungsleistungFormBean> pruefungenBeans;

	/* Logik Start */

	@Override
	public void validate() {
	}

	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungFormBean>());

		for (Student student : getStudentService().getAllStudenten(
				getSelectedManipel())) {
			if (student == null)
				continue;
			else {
				Optional<Note> alteNote = getPruefungService().getAktuelleNote(
						student, getSelectedPruefungsfach());

				getPruefungenBeans().add(
						new PruefungsleistungFormBean(student, alteNote, null));
			}
		}

		Collections.sort(getPruefungenBeans());
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
			System.out.println(p.getStudent());
			if (!Note.isValid(p.getNote())) {
				addFieldError("pruefungenBeans[" + i + "].note",
						"Keine gültige Note");
			}
			i++;
		}

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			for (PruefungsleistungFormBean p : pruefungenBeans) {
				getPruefungService().addPruefungsleistung(
						getSelectedPruefung(), p.getStudent(),
						Note.getNote(p.getNote()));
			}
			// TODO show the protokoll
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

	public void setPruefungenBeans(
			List<PruefungsleistungFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
