package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.view.formbean.PruefungsleistungAendernFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenAendernAction extends AbstractFormAction {

	private List<PruefungsleistungAendernFormBean> pruefungenBeans;

	/* Custom Logik Start */

	/**
	 * Fülle die pruefungenBeans
	 */
	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungAendernFormBean>());

		for (Student student : getStudentService().getAllStudenten(
				getSelectedManipel())) {
			if (student == null)
				continue;
			else {
				// TODO logik einbauen, damit der service benutzt wird

				List<Pruefungsleistung> leistungen = getPruefungService()
						.getAllPruefungsleistungen(getSelectedPruefungsfach(),
								student);

				getPruefungenBeans().add(
						new PruefungsleistungAendernFormBean(student,
								leistungen));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/* Custom Logik Ende */
	/* Logik Start */

	/* Logik Ende */
	/* Actions Start */

	public String show() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	public boolean isWriteable(Long id) {
		return true;
		// return getPruefungService().isPruefungsleistungEditable(id);
	}

	public String save() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		int i = 0;
		for (PruefungsleistungAendernFormBean p : pruefungenBeans) {
			// TODO hier validieren

			int k = 0;
			for (Pruefungsleistung pl : p.getPruefungsleistungen()) {
				if (getPruefungService()
						.isPruefungsleistungEditable(pl.getId())
						&& !Note.isValid(String.valueOf(pl.getNote().getNote()))) {
					addFieldError("pruefungenBeans[" + i + "].note" + k,
							"Keine gültige Note");
				}
				k++;
			}
			i++;
		}

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			for (PruefungsleistungAendernFormBean p : pruefungenBeans) {
				for (Pruefungsleistung pl : p.getPruefungsleistungen()) {
					if (getPruefungService().isPruefungsleistungEditable(
							pl.getId())) {
						getPruefungService().updatePruefungsleistung(
								pl.getId(), pl.getNote());
					}
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

	public void setPruefungenBeans(
			List<PruefungsleistungAendernFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
