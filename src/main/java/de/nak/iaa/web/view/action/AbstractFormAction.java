package de.nak.iaa.web.view.action;

import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.web.util.DataHelper;

/**
 * Abstrakte Action Klasse für Formulare, die die Vorauswahl von Prüfungen und Prüfungsfach
 * verarbeitet und für die Kind-Actions nutzbar macht.
 * 
 * (Ignoriert hierbei die Tatsache, dass nicht immer eine Prüfung ausgewählt werden muss. Mit
 * Technologien wie Mixins oder Traits wäre der Inhalt dieser Klasse besser umgesetzt gewesen. Diese
 * Technologien sind in Java aber nicht ohne weiteres vorhanden.)
 * 
 * @author Christopher Biel
 */
public abstract class AbstractFormAction extends AbstractAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private Pruefungsfach selectedPruefungsfach;
	private Pruefung selectedPruefung;
	private String pruefungsfach;
	private String pruefung;

	/* Custom Logik Start */

	/* Custom Logik Ende */
	/* Logik Start */

	@Override
	public void prepare() throws Exception {
		setPruefung(DataHelper.stringArrayToString(getParameters().get("pruefung")));
		setPruefungsfach(DataHelper.stringArrayToString(getParameters().get("pruefungsfach")));

		if (getParameters().containsKey("pruefungsfach")
				&& !DataHelper.stringArrayToString(getParameters().get("pruefungsfach")).equals("")) {
			selectedPruefungsfach = getPruefungService().getPruefungsfachById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters().get("pruefungsfach")))).get();
		}
		if (getParameters().containsKey("pruefung")
				&& !DataHelper.stringArrayToString(getParameters().get("pruefung")).equals("")) {
			selectedPruefung = getPruefungService().getPruefungById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters().get("pruefung")))).get();
		}
	}

	/* Logik Ende */
	/* Properties */

	public Pruefungsfach getSelectedPruefungsfach() {
		return selectedPruefungsfach;
	}

	public void setSelectedPruefungsfach(Pruefungsfach selectedPruefungsfach) {
		this.selectedPruefungsfach = selectedPruefungsfach;
	}

	public Pruefung getSelectedPruefung() {
		return selectedPruefung;
	}

	public void setSelectedPruefung(Pruefung selectedPruefung) {
		this.selectedPruefung = selectedPruefung;
	}

	public String getPruefungsfach() {
		return pruefungsfach;
	}

	public void setPruefungsfach(String pruefungsfach) {
		this.pruefungsfach = pruefungsfach;
	}

	public String getPruefung() {
		return pruefung;
	}

	public void setPruefung(String pruefung) {
		this.pruefung = pruefung;
	}
}
