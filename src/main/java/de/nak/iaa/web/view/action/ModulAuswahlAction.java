package de.nak.iaa.web.view.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;

/**
 * Actionklasse, die die Auswahl des Prüfungsfachs und der konkreten Prüfung steuert
 * 
 * Das speichern der gewählten Werte erfolgt über die Parentklasse "AbstractFormAction"
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
public class ModulAuswahlAction extends AbstractFormAction {

	private static final long serialVersionUID = 1L;

	private static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	private List<Pruefungsfach> pruefungsfaecher;
	private List<Pruefung> pruefungen;

	/* Custom Logik Start */

	public String getCurrentNamespace() {
		return ServletActionContext.getActionMapping().getNamespace();
	}

	/* Custom Logik Ende */
	/* Actions Start */

	/**
	 * Zeige die Module(Prüfungsfächer) auf Basis des gewählten Manipels
	 * 
	 * @return
	 */
	public String showSelectModul() {
		if (isManipelNotSelected()) {
			setTargetUrl(ServletActionContext.getRequest().getRequestURL().toString());
			return NO_MANIPEL_SELECTED;
		}

		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher((Manipel) getSession().get("selectedManipel")));
		return Action.SUCCESS;
	}

	/**
	 * Zeige die Prüfungen auf Basis des gewählten Prüfungsfachs und des gewählten Manipels
	 * 
	 * @return
	 */
	public String showSelectPruefung() {
		if (isManipelNotSelected()) {
			setTargetUrl("/");
			return NO_MANIPEL_SELECTED;
		}

		setPruefungen(getPruefungService().getAllPruefungen(getSelectedPruefungsfach()));
		return Action.SUCCESS;
	}

	/* Actions Ende */
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
