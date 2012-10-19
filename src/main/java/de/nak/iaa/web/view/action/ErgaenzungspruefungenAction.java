package de.nak.iaa.web.view.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.web.view.formbean.ErgaenzungspruefungsBean;

@SuppressWarnings("serial")
public class ErgaenzungspruefungenAction extends ActionSupport {

	private Map<String, ErgaenzungspruefungsBean> exams;

	@Override
	public void validate() {
		if (exams != null) {
			for (Map.Entry<String, ErgaenzungspruefungsBean> entry : exams
					.entrySet()) {
				if (!entry
						.getValue()
						.getResultPercent()
						.matches(
								"[123][.][037]|[0.7]|[4.0]|[5.0]|[6.0]|[123456]")) {
					System.out.println(entry.getValue().getResultPercent());
					addFieldError("exams[" + entry.getKey() + "].text",
							"Falsche Eingabe");
				}
			}
		}
	}

	public String save() throws Exception {
		for (Map.Entry<String, ErgaenzungspruefungsBean> entry : exams
				.entrySet()) {
			System.out.println(entry.getValue().getResultPercent());
		}
		return "success";
	}

	public String show() throws Exception {
		return "success";
	}

	public Map<String, ErgaenzungspruefungsBean> getExams() {
		return exams;
	}

	public void setExams(Map<String, ErgaenzungspruefungsBean> exams) {
		this.exams = exams;
	}

}
