package de.nak.iaa.web.view.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.web.util.DataHelper;

/**
 * Aktionklasse, die die Auswahl des Manipels steuert
 * 
 * @author Christopher Biel
 */
public class ManipelAendernAction extends AbstractAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private String selectedManipel;
	private List<Manipel> manipel;

	private static String refererUrl;

	/* Logik Start */

	@Override
	public void prepare() throws Exception {
		setManipel(getStudentService().getAllManipel());
	}

	@Override
	public void validate() {
		if (selectedManipel != null)
			if (selectedManipel.equals("-1"))
				addFieldError("selectedManipel", "Sie müssen einen Manipel auswählen");
	}

	/* Logik Ende */
	/* Actions Start */

	/**
	 * Zeige die Manipelauswahl
	 * 
	 * @return
	 */
	public String show() {
		// Wechsle nur zur "show" action, wenn noch kein manipel ausgewählt war
		// sonst auf die hier spezifizierte seite
		if (ServletActionContext.getRequest().getHeader("referer") == null && getSelectedManipel() == null) {
			setRefererUrl("show");
		} else {
			// setRefererUrl(ServletActionContext.getRequest().getHeader("referer"));
			setRefererUrl("/");
		}
		if (getSession().containsKey("selectedManipel") && getSession().get("selectedManipel") != null)
			selectedManipel = getSession().get("selectedManipel").toString();
		return Action.SUCCESS;
	}

	/**
	 * Speichere die Manipelauswahl in der Session
	 * 
	 * @return
	 */
	public String save() {
		// speicher den aktuell gewählten manipel in der session
		String[] manipelParts = selectedManipel.split(" ", 2);
		getSession().put(
				"selectedManipel",
				manipel.get(manipel.indexOf(new Manipel(Integer.valueOf(manipelParts[0]), Studienrichtung
						.valueOf(manipelParts[1])))));
		return Action.SUCCESS;
	}

	/**
	 * Setzte die referrerUrl, sodass nach der Manipeleingabe zu der Seite zurückgekehrt werden kann
	 * 
	 * @return
	 */
	public String error() {
		if (getParameters().containsKey("target")) {
			setRefererUrl(DataHelper.stringArrayToString(getParameters().get("target")));
		}
		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public void setSelectedManipel(String selectedManipel) {
		this.selectedManipel = selectedManipel;
	}

	public List<Manipel> getManipel() {
		return manipel;
	}

	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		ManipelAendernAction.refererUrl = refererUrl;
	}

	public void setManipel(List<Manipel> manipel) {
		this.manipel = manipel;
	}
}