package de.nak.iaa.web.view.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.business.mock.MockStudentService;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.fachwert.Studienrichtung;
import de.nak.iaa.web.util.DataHelper;

@SuppressWarnings("serial")
public class ManipelAendernAction extends ActionSupport implements
		SessionAware, ParameterAware {

	private String selectedManipel;
	private static String refererUrl;

	/* Logik */
	@Override
	public String execute() throws Exception {
		setStudentService(new MockStudentService());
		return super.execute();
	}

	@Override
	public void validate() {
		if (selectedManipel != null)
			if (selectedManipel.equals("-1"))
				addFieldError("selectedManipel",
						"Sie müssen einen Manipel auswählen");
	}

	/* Actions */
	public String show() {
		if (ServletActionContext.getRequest().getHeader("referer") == null) {
			setRefererUrl("show");
		} else {
			setRefererUrl(ServletActionContext.getRequest()
					.getHeader("referer"));
		}
		if (getSession().containsKey("selectedManipel")
				&& getSession().get("selectedManipel") != null)
			selectedManipel = getSession().get("selectedManipel").toString();
		return Action.SUCCESS;
	}

	public String save() {
		String[] manipelParts = selectedManipel.split(" ", 2);
		getSession().put(
				"selectedManipel",
				new Manipel(Integer.valueOf(manipelParts[0]), Studienrichtung
						.valueOf(manipelParts[1])));
		return Action.SUCCESS;
	}

	public String error() {
		if (getParameters().containsKey("target")) {
			setRefererUrl(DataHelper.stringArrayToString(getParameters().get(
					"target")));
		}
		return Action.SUCCESS;
	}

	/* Session Management */
	private Map<String, Object> session;

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/* Parameter Management */
	private Map<String, String[]> parameters;

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	/* Service Management */
	private StudentService studentService;

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/* Properties */
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
}