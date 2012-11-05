package de.nak.iaa.web.view.formbean;

import java.util.Date;

import de.nak.iaa.server.entity.Student;

/**
 * FormBean, die einerseits Daten für das Erfassen von Ergänzungsprüfungen bereitstellt aber auch
 * zur Auswertung der Eingaben verwendet wird
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
public class ErgaenzungspruefungsFormBean extends AbstractFormBean {

	private String resultPercent;
	private Date datum;
	private Date ergDatum;

	public ErgaenzungspruefungsFormBean() {
	}

	public ErgaenzungspruefungsFormBean(Student student, Date datum, String resultPercent) {
		this.student = student;
		this.datum = datum;
		this.resultPercent = resultPercent;
	}

	public String getResultPercent() {
		return resultPercent;
	}

	public void setResultPercent(String resultPercent) {
		this.resultPercent = resultPercent;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Date getErgDatum() {
		return ergDatum;
	}

	public void setErgDatum(Date ergDatum) {
		this.ergDatum = ergDatum;
	}
}