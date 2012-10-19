package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ManipelAendernAction extends ActionSupport implements SessionAware {

	private List<String> manipel;
	private Map<String, Object> session;
	private String selectedManipel;
	private static String refererUrl;

	public ManipelAendernAction() {
		setManipel(new ArrayList<String>());
		getManipel().add("i09");
		getManipel().add("w09");
		getManipel().add("b09");
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
		return "success";
	}

	public String save() {
		getSession().put("selectedManipel", selectedManipel);
		return "success";
	}

	public String error() {
		if (ServletActionContext.getRequest().getParameter("target") != null) {
			setRefererUrl(ServletActionContext.getRequest()
					.getParameter("target").toString());
		}
		return "success";
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

	public List<String> getManipel() {
		return manipel;
	}

	public void setManipel(List<String> manipel) {
		this.manipel = manipel;
	}

	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		ManipelAendernAction.refererUrl = refererUrl;
	}
}
