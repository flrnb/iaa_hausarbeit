package de.nak.iaa.web.view.formbean;

//@Validations(regexFields = { @RegexFieldValidator(type = ValidatorType.SIMPLE, fieldName = "text", message = "falsche Note", expression = "[123][.][037]|[0.7]|[4.0]|[5.0]|[6.0]|[123456]") })
public class ErgaenzungspruefungsBean {

	private final String examId;
	private final String matrikelNummer;
	private final String name;
	private String resultPercent;

	public ErgaenzungspruefungsBean(String examId, String matrikelNummer,
			String name, String resultPercent) {
		this.examId = examId;
		this.matrikelNummer = matrikelNummer;
		this.name = name;
		this.resultPercent = resultPercent;
	}

	public String getExamId() {
		return examId;
	}

	public String getResultPercent() {
		return resultPercent;
	}

	public void setResultPercent(String resultPercent) {
		this.resultPercent = resultPercent;
	}

	public String getMatrikelNummer() {
		return matrikelNummer;
	}

	public String getName() {
		return name;
	}
}