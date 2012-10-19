package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ManipelAendernAction extends ActionSupport implements SessionAware {

	private List<String> manipel;
	private Map<String, Object> session;
	private String selectedManipel;

	public ManipelAendernAction() {
		// manipel = new ArrayList<String>();
		// manipel.add("i09");
		// manipel.add("w09");
		// manipel.add("b09");
		setManipel(new ArrayList<String>());
		getManipel().add("i09");
		getManipel().add("w09");
		getManipel().add("b09");
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String show() {
		System.out.println(getSession().get("selectedManipel"));
		if (getSession().containsKey("selectedManipel")
				&& getSession().get("selectedManipel") != null)
			selectedManipel = getSession().get("selectedManipel").toString();
		return "success";
	}

	public String save() {
		getSession().put("selectedManipel", selectedManipel);
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
}
