package de.nak.iaa.web.view.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;

@SuppressWarnings("serial")
public class ManipelAendernAction extends ActionSupport implements
		SessionAware, ParameterAware {

	private StudentService studentService;
	private Map<String, Object> session;
	private Map<String, String[]> parameters;
	private String selectedManipel;
	private static String refererUrl;

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	@Override
	public void validate() {
		if (selectedManipel != null)
			if (selectedManipel.equals("-1"))
				addFieldError("selectedManipel",
						"Sie müssen einen Manipel auswählen");
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String show() {
		if (ServletActionContext.getRequest().getHeader("referer") == null) {
			setRefererUrl("show");
		} else {
			setRefererUrl(ServletActionContext.getRequest()
					.getHeader("referer"));
		}
		System.out.println(getRefererUrl());
		if (getSession().containsKey("selectedManipel")
				&& getSession().get("selectedManipel") != null)
			selectedManipel = getSession().get("selectedManipel").toString();
		return Action.SUCCESS;
	}

	public String save() {
		getSession().put("selectedManipel", selectedManipel);
		return Action.SUCCESS;
	}

	public String error() {
		if (getParameters().containsKey("target")) {
			setRefererUrl(getParameters().get("target").toString());
		}
		return Action.SUCCESS;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getSelectedManipel() {
		return selectedManipel;
	}

	public void setSelectedManipel(String selectedManipel) {
		this.selectedManipel = selectedManipel;
	}

	public List<Manipel> getManipel() {
		return studentService.getAllManipel();
	}

	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		ManipelAendernAction.refererUrl = refererUrl;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
}
