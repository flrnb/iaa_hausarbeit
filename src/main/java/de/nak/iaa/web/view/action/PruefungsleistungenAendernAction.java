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
import de.nak.iaa.web.util.DataHelper;
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

				if (!leistungen.isEmpty())
					getPruefungenBeans().add(
							new PruefungsleistungAendernFormBean(student,
									leistungen));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	public boolean isWriteable(Long id) {
		return getPruefungService().isPruefungsleistungEditable(id);
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
				if (pl.getNote() == null)
					continue;
				if ((isWriteable(pl.getId()) && !Note.isValid(String.valueOf(pl
						.getNote().getNote())))) {
					addFieldError("pruefungenBeans[" + i
							+ "].pruefungsleistungen[" + k + "].note",
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
			List<PruefungsleistungAenderung> aenderungen = new ArrayList<PruefungsleistungAenderung>();
			for (PruefungsleistungAendernFormBean p : pruefungenBeans) {
				for (Pruefungsleistung pl : p.getPruefungsleistungen()) {
					if (getPruefungService().isPruefungsleistungEditable(
							pl.getId())) {
						aenderungen.add(new PruefungsleistungAenderung.Update(
								pl.getId(), pl.getNote()));
					}
				}
			}
			if (!aenderungen.isEmpty()) {
				try {
					getPruefungService().updatePruefungsleistungen(aenderungen);
				} catch (IllegalUpdateException e) {
					// TODO und was soll ich hiermit bitte machen??!!
					e.printStackTrace();
				}
			}
			return Action.SUCCESS;
		}
	}

	public String delete() {
		if (getParameters().containsKey("deleteId")) {
			List<PruefungsleistungAenderung> aenderungen = new ArrayList<PruefungsleistungAenderung>();
			aenderungen.add(new PruefungsleistungAenderung.Delete(Long
					.getLong(DataHelper.stringArrayToString(getParameters()
							.get("deleteId")))));
			try {
				getPruefungService().updatePruefungsleistungen(aenderungen);
			} catch (IllegalUpdateException e) {
				// TODO behandle, wenn keine prüfungsleistung vorhanden ist
				// (fehlerseite)
				// dafür referrer holen und an die fehlerseite übergeben
				e.printStackTrace();
			}
		} else {
			// TODO hier muss iwie ne fehlerseite hin
		}
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
