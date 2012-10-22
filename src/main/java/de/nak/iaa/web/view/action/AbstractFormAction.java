package de.nak.iaa.web.view.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.web.util.DataHelper;

@SuppressWarnings("serial")
public abstract class AbstractFormAction extends ActionSupport implements SessionAware, ParameterAware, Preparable {

	protected static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	private Pruefungsfach selectedPruefungsfach;
	private Pruefung selectedPruefung;
	private String pruefungsfach;
	private String pruefung;

	/* Custom Logik Start */

	public boolean isManipelSelected() {
		return (!getSession().containsKey("selectedManipel") || getSession().get("selectedManipel") == null || getSession()
				.get("selectedManipel").equals(""));
	}

	public String getRequestUrl() {
		return ServletActionContext.getRequest().getRequestURL().toString();
	}

	public Manipel getSelectedManipel() {
		return (Manipel) getSession().get("selectedManipel");
	}

	/* Custom Logik Ende */
	/* Logik Start */

	@Override
	public void prepare() throws Exception {
		setPruefung(DataHelper.stringArrayToString(parameters.get("pruefung")));
		setPruefungsfach(DataHelper.stringArrayToString(parameters.get("pruefungsfach")));

		if (getParameters().containsKey("pruefungsfach") && !getParameters().get("pruefungsfach").equals("")) {
			selectedPruefungsfach = getPruefungService().getPruefungsfachById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters().get("pruefungsfach")))).get();
		}
		if (getParameters().containsKey("pruefung") && !getParameters().get("pruefung").equals("")) {
			selectedPruefung = getPruefungService().getPruefungById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters().get("pruefung")))).get();
		}
	}

	/* Logik Ende */
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
	/* Ziel Url (für Fehlerfall) Start */

	private String targetUrl;

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	/* Ziel Url (für Fehlerfall) Ende */
	/* Properties */

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

	public String getPruefungsfach() {
		return pruefungsfach;
	}

	public void setPruefungsfach(String pruefungsfach) {
		this.pruefungsfach = pruefungsfach;
	}

	public String getPruefung() {
		return pruefung;
	}

	public void setPruefung(String pruefung) {
		this.pruefung = pruefung;
	}
}
