package de.nak.iaa.web.view.action;

import java.util.Locale;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.MessageSource;

import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;

/**
 * Abstrakte Action. Implementiert um Schicht zwischen ActionSupport und eigenen Actions zu
 * ermöglichen. Bietet funktionalitäten an, die in jeder Action in dieser Anwendung benötigt werden
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
public abstract class AbstractAction extends ActionSupport implements SessionAware, ParameterAware {

	private static final long serialVersionUID = 1L;

	protected static final String NO_MANIPEL_SELECTED = "noManipelSelected";
	protected static final String EXCEPTION_OCCURED = "exception";
	protected static final String SPECIFIC_EXCEPTION_OCCURED = "specException";

	private int occuredErrorCode;

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
	public boolean isManipelNotSelected() {
		return (!getSession().containsKey("selectedManipel") || getSession().get("selectedManipel") == null || getSession()
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

	/**
	 * Hole die Message zu dem übergebenen key
	 * 
	 * @param key
	 * @return
	 */
	public String getMsg(String key) {
		return getMessageSource().getMessage(key, new Object[] {}, Locale.getDefault());
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
	/* Messages Start */

	private MessageSource messageSource;

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public int getOccuredErrorCode() {
		return occuredErrorCode;
	}

	public void setOccuredErrorCode(int occuredErrorCode) {
		this.occuredErrorCode = occuredErrorCode;
	}

	/* Messages Ende */
}
