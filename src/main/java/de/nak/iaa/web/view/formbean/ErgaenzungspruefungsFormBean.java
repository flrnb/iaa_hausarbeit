package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.entity.Student;

//@Validations(regexFields = { @RegexFieldValidator(type = ValidatorType.SIMPLE, fieldName = "text", message = "falsche Note", expression = "[123][.][037]|[0.7]|[4.0]|[5.0]|[6.0]|[123456]") })
public class ErgaenzungspruefungsFormBean extends AbstractFormBean {

	private String resultPercent;

	public ErgaenzungspruefungsFormBean() {
	}

	public ErgaenzungspruefungsFormBean(Student student, String resultPercent) {
		this.student = student;
		this.resultPercent = resultPercent;
	}

	public String getResultPercent() {
		return resultPercent;
	}

	public void setResultPercent(String resultPercent) {
		this.resultPercent = resultPercent;
	}
}