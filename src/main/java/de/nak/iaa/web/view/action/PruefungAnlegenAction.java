package de.nak.iaa.web.view.action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.entity.Dozent;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.web.util.DataHelper;
import de.nak.iaa.web.util.MessageKey;

public class PruefungAnlegenAction extends AbstractAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private List<Pruefungsfach> pruefungsfaecher;
	private String formPruefungsfach;
	private List<Dozent> dozenten;
	private String formDozent;
	private Date formDate;

	@Override
	public void prepare() throws Exception {
		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(
				getSelectedManipel()));
	}

	/* Actions Start */

	public String show() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		return Action.SUCCESS;
	}

	public String save() {
		boolean hasError = false;
		if (getFormDate() == null) {
			addFieldError("formDate", getMsg(MessageKey.ERR_EMP_DATUM));
			hasError = true;
		}
		if (getFormDozent() == null || getFormDozent().equals("")) {
			addFieldError("formDozent", getMsg(MessageKey.ERR_EMP_DOZENT));
			hasError = true;
		}
		if (getFormPruefungsfach() == null || getFormPruefungsfach().equals("")) {
			addFieldError("formPruefungsfach",
					getMsg(MessageKey.ERR_EMP_PRUEFUNGSFACH));
			hasError = true;
		}

		if (!hasError) {
			// TODO dozenten holen
			getPruefungService().addPruefung(
					getPruefungService().getPruefungsfachById(
							Long.valueOf(DataHelper
									.stringArrayToString(getParameters().get(
											"formPruefungsfachKey")))).get(),
					getFormDate(), null);

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
