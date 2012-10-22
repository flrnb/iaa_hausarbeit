package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.view.formbean.PruefungsleistungAendernFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenAendernAction extends AbstractFormAction {

	private List<PruefungsleistungAendernFormBean> pruefungenBeans;

	/* Logik Start */

	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungAendernFormBean>());

		for (Student student : getStudentService().getAllStudenten(
				(Manipel) getSession().get("selectedManipel"))) {
			if (student == null)
				continue;
			else {
				// TODO logik einbauen, damit der service benutzt wird

				// getPruefungenBeans().add(
				// new PruefungsleistungFormBean(student.getMatrikelNr(),
				// student.getVorname() + " " + student.getName(),
				// , null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/* Logik Ende */
	/* Actions Start */

	public String show() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}
		return Action.SUCCESS;
	}

	public String save() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		int i = 0;
		for (PruefungsleistungAendernFormBean p : pruefungenBeans) {

			// TODO hier validieren
			if (Note.getNote(p.getNote1()) != null) {
				addFieldError("pruefungenBeans[" + i + "].note1",
						"Keine gültige Note");
			}
			if (Note.getNote(p.getNote2()) != null) {
				addFieldError("pruefungenBeans[" + i + "].note2",
						"Keine gültige Note");
			}
			if (Note.getNote(p.getNote3()) != null) {
				addFieldError("pruefungenBeans[" + i + "].note3",
						"Keine gültige Note");
			}
			// TODO process the form and save the noten
			i++;
		}

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		}
		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<PruefungsleistungAendernFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(
			List<PruefungsleistungAendernFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
