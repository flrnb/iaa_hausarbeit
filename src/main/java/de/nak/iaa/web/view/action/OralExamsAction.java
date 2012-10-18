package de.nak.iaa.web.view.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.web.view.bean.OralExamBean;

@SuppressWarnings("serial")
public class OralExamsAction extends ActionSupport {

	private Map<String, OralExamBean> exams;

	@Override
	public void validate() {
		if (exams != null) {
			for (Map.Entry<String, OralExamBean> entry : exams.entrySet()) {
				if (!entry
						.getValue()
						.getText()
						.matches(
								"[123][.][037]|[0.7]|[4.0]|[5.0]|[6.0]|[123456]")) {
					System.out.println(entry.getValue().getText());
					addFieldError("exams[" + entry.getKey() + "].text",
							"Falsche Eingabe");
				}
			}
		}
	}

	public String save() throws Exception {
		for (Map.Entry<String, OralExamBean> entry : exams.entrySet()) {
			System.out.println(entry.getValue().getText());
		}
		return "success";
	}

	public String show() throws Exception {
		return "success";
	}

	public Map<String, OralExamBean> getExams() {
		return exams;
	}

	public void setExams(Map<String, OralExamBean> exams) {
		this.exams = exams;
	}

}
