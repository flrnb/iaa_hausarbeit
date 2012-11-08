package de.nak.iaa.web.view.action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.business.DozentService;
import de.nak.iaa.server.entity.Dozent;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.web.util.MessageKey;

public class PruefungAnlegenAction extends AbstractAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private DozentService dozentService;

	public DozentService getDozentService() {
		return dozentService;
	}

	public void setDozentService(DozentService dozentService) {
		this.dozentService = dozentService;
	}

	private List<Pruefungsfach> pruefungsfaecher;
	private String formPruefungsfach;
	private List<Dozent> dozenten;
	private String formDozent;
	private Date formDate;

	@Override
	public void prepare() throws Exception {
		// Fülle die mit den Formularfeldern korrespondierenden Attribute
		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(getSelectedManipel()));
		setDozenten(getDozentService().getAllDozenten());
	}

	/**
	 * Validiere die Eingabedaten und gebe zurück, ob ein Fehler aufgetreten ist <br>
	 * Fügt bei einem Problem, dem Feld einen FieldError hinzu
	 * 
	 * @return
	 */
	private boolean validateForm() {
		boolean hasError = false;
		if (getFormDate() == null) {
			addFieldError("formDate", getMsg(MessageKey.ERR_EMP_DATUM));
			hasError = true;
		} else {
			if (getFormDate().getTime() < 1104559200) { // Datum vor 1.1.2005
				setFormDate(null);
				addFieldError("formDate", getMsg(MessageKey.ERR_UNGUELTIGES_DATUM));
				hasError = true;
			}
		}
		if (getFormDozent() == null || getFormDozent().equals("") || getFormDozent().equals("-1")) {
			addFieldError("formDozent", getMsg(MessageKey.ERR_EMP_DOZENT));
			hasError = true;
		}
		if (getFormPruefungsfach() == null || getFormPruefungsfach().equals("") || getFormPruefungsfach().equals("-1")) {
			addFieldError("formPruefungsfach", getMsg(MessageKey.ERR_EMP_PRUEFUNGSFACH));
			hasError = true;
		}
		return hasError;
	}

	/* Actions Start */

	/**
	 * Zeige das Formular an
	 * 
	 * @return
	 */
	public String show() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		return Action.SUCCESS;
	}

	/**
	 * Validiere (und speichere) die Eingabedaten via Service
	 * 
	 * @return
	 */
	public String save() {
		if (!validateForm()) {
			getPruefungService().addPruefung(
					getPruefungService().getPruefungsfachById(Long.valueOf(getFormPruefungsfach())).get(),
					getFormDate(), getDozentService().getDozentById(Long.valueOf(getFormDozent())).get());

			return Action.SUCCESS;
		}

		return Action.INPUT;
	}

	/* Actions Ende */
	/* Properties */

	public List<Pruefungsfach> getPruefungsfaecher() {
		return pruefungsfaecher;
	}

	public void setPruefungsfaecher(List<Pruefungsfach> pruefungsfaecher) {
		this.pruefungsfaecher = pruefungsfaecher;
	}

	public String getFormPruefungsfach() {
		return formPruefungsfach;
	}

	public void setFormPruefungsfach(String formPruefungsfach) {
		this.formPruefungsfach = formPruefungsfach;
	}

	public List<Dozent> getDozenten() {
		return dozenten;
	}

	public void setDozenten(List<Dozent> dozenten) {
		this.dozenten = dozenten;
	}

	public String getFormDozent() {
		return formDozent;
	}

	public void setFormDozent(String formDozent) {
		this.formDozent = formDozent;
	}

	public Date getFormDate() {
		return formDate;
	}

	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}
}
