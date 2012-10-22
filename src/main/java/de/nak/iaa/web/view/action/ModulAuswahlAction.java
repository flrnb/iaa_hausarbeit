package de.nak.iaa.web.view.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.web.util.DataHelper;

@SuppressWarnings("serial")
public class ModulAuswahlAction extends ActionSupport implements
		ParameterAware, SessionAware {
	private static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	private List<Pruefungsfach> pruefungsfaecher;
	private List<Pruefung> pruefungen;
	private Pruefungsfach selectedPruefungsfach;
	private Pruefung selectedPruefung;

	public String showSelectModul() {
		if (!getSession().containsKey("selectedManipel")
				|| getSession().get("selectedManipel") == null
				|| getSession().get("selectedManipel").equals("")) {
			setTargetUrl(ServletActionContext.getRequest().getRequestURL()
					.toString());
			return NO_MANIPEL_SELECTED;
		}
		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(
				(Manipel) getSession().get("selectedManipel")));
		return Action.SUCCESS;
	}

	public String showSelectPruefung() {
		if (!getSession().containsKey("selectedManipel")
				|| getSession().get("selectedManipel") == null
				|| getSession().get("selectedManipel").equals("")) {
			setTargetUrl(ServletActionContext.getRequest().getRequestURL()
					.toString());
			return NO_MANIPEL_SELECTED;
		}

		if (getParameters().containsKey("pruefungsfach")
				&& !getParameters().get("pruefungsfach").equals("")) {
			selectedPruefungsfach = getPruefungService().getPruefungsfachById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters()
							.get("pruefungsfach"))));
		}

		setPruefungen(getPruefungService().getAllPruefung(
				(Manipel) getSession().get("selectedManipel"),
				getSelectedPruefungsfach()));
		return Action.SUCCESS;
	}

	/* Session Management Start */

	private Map<String, Object> session;

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/* Session Management Ende */
	/* Parameter Management Start */

	private Map<String, String[]> parameters;

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	/* Parameter Management Ende */
	/* Ziel Url (für Fehlerfall) Start */

	private String targetUrl;

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	/* Ziel Url (für Fehlerfall) Ende */
	/* Service Management Start */
	private StudentService studentService;

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	private PruefungService pruefungService;

	public PruefungService getPruefungService() {
		return pruefungService;
	}

	public void setPruefungService(PruefungService pruefungService) {
		this.pruefungService = pruefungService;
	}

	/* Service Management Ende */
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

	public Pruefungsfach getSelectedPruefungsfach() {
		return selectedPruefungsfach;
	}

	public void setSelectedPruefungsfach(Pruefungsfach selectedPruefungsfach) {
		this.selectedPruefungsfach = selectedPruefungsfach;
	}

	public Pruefung getSelectedPruefung() {
		return selectedPruefung;
	}

	public void setSelectedPruefung(Pruefung selectedPruefung) {
		this.selectedPruefung = selectedPruefung;
	}

}
