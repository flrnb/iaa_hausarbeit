package de.nak.iaa.web.view.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;

public abstract class AbstractAction extends ActionSupport implements
		SessionAware, ParameterAware {

	private static final long serialVersionUID = 1L;

	protected static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	/* Custom Logik Start */

	/**
	 * Lese den ausgewählten Manipel aus der Session
	 * 
	 * @return
	 */
	public Manipel getSelectedManipel() {
		return (Manipel) getSession().get("selectedManipel");
	}

	/**
	 * Schaue, ob ein Manipel ausgewählt ist
	 * 
	 * @return
	 */
	public boolean isManipelSelected() {
		return (!getSession().containsKey("selectedManipel")
				|| getSession().get("selectedManipel") == null || getSession()
				.get("selectedManipel").equals(""));
	}

	/**
	 * Lese die aktuelle RequestUrl
	 * 
	 * @return
	 */
	public String getRequestUrl() {
		return ServletActionContext.getRequest().getRequestURL().toString();
	}

	/* Custom Logik Ende */
	/* Ziel Url (für Fehlerfall) Start */

	private String targetUrl;

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	/* Ziel Url (für Fehlerfall) Ende */
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
}
