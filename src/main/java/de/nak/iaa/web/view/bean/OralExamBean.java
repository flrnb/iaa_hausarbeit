package de.nak.iaa.web.view.bean;

//@Validations(regexFields = { @RegexFieldValidator(type = ValidatorType.SIMPLE, fieldName = "text", message = "falsche Note", expression = "[123][.][037]|[0.7]|[4.0]|[5.0]|[6.0]|[123456]") })
public class OralExamBean {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
