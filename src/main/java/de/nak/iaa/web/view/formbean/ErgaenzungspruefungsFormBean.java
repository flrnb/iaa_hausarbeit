package de.nak.iaa.web.view.formbean;

//@Validations(regexFields = { @RegexFieldValidator(type = ValidatorType.SIMPLE, fieldName = "text", message = "falsche Note", expression = "[123][.][037]|[0.7]|[4.0]|[5.0]|[6.0]|[123456]") })
public class ErgaenzungspruefungsFormBean extends AbstractFormBean {

	private String resultPercent;

	public ErgaenzungspruefungsFormBean() {
	}

	public ErgaenzungspruefungsFormBean(Integer matrikelNummer, String name,
			String resultPercent) {
		this.matrikelNummer = matrikelNummer;
		this.name = name;
		this.resultPercent = resultPercent;
	}

	public String getResultPercent() {
		return resultPercent;
	}

	public void setResultPercent(String resultPercent) {
		this.resultPercent = resultPercent;
	}
}