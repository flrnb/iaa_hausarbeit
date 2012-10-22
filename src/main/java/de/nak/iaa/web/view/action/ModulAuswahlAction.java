package de.nak.iaa.web.view.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;

@SuppressWarnings("serial")
public class ModulAuswahlAction extends AbstractFormAction implements
		ParameterAware, SessionAware {
	private static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	private List<Pruefungsfach> pruefungsfaecher;
	private List<Pruefung> pruefungen;

	public String showSelectModul() {
		if (isManipelSelected()) {
			setTargetUrl(ServletActionContext.getRequest().getRequestURL()
					.toString());
			return NO_MANIPEL_SELECTED;
		}
		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(
				(Manipel) getSession().get("selectedManipel")));
		return Action.SUCCESS;
	}

	public String showSelectPruefung() {
		if (isManipelSelected()) {
			setTargetUrl("/");
			return NO_MANIPEL_SELECTED;
		}

		setPruefungen(getPruefungService().getAllPruefungen(
				getSelectedPruefungsfach()));
		return Action.SUCCESS;
	}

	/* Properties */
	public List<Pruefungsfach> getPruefungsfaecher() {
		return pruefungsfaecher;
	}

	public void setPruefungsfaecher(List<Pruefungsfach> pruefungsfaecher) {
		this.pruefungsfaecher = pruefungsfaecher;
	}

	public List<Pruefung> getPruefungen() {
		return pruefungen;
	}

	public void setPruefungen(List<Pruefung> pruefungen) {
		this.pruefungen = pruefungen;
	}
}
