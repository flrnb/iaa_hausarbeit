package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.web.util.MessageKey;
import de.nak.iaa.web.view.formbean.ErgaenzungspruefungsFormBean;

/**
 * Actionklasse, die für die Verarbeitung (Anzeige, Änderung) von Ergänzungsprüfungen zuständig ist
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
public class ErgaenzungspruefungenAction extends AbstractFormAction {

	private static final long serialVersionUID = 1L;

	private List<ErgaenzungspruefungsFormBean> pruefungenBeans;

	/* Custom Logik Start */

	/**
	 * Fülle die pruefungenBeans
	 */
	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<ErgaenzungspruefungsFormBean>());

		for (Entry<Student, Date> ergaenzungspruefung : getPruefungService().getAllErgaenzungsPruefungsStudenten(
				getSelectedManipel(), getSelectedPruefungsfach()).entrySet()) {
			if (ergaenzungspruefung == null)
				continue;
			else {
				getPruefungenBeans().add(
						new ErgaenzungspruefungsFormBean(ergaenzungspruefung.getKey(), ergaenzungspruefung.getValue(),
								null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/**
	 * Validiere die Eingabedaten für die Zeilen, wo mindestens eines der Felder gefüllt ist<br>
	 * Fügt bei einem Problem, dem Feld einen FieldError hinzu
	 */
	private void validateForm() {
		int i = 0;
		for (ErgaenzungspruefungsFormBean p : pruefungenBeans) {
			if (p.getErgDatum() != null || !p.getResultPercent().isEmpty()) {
				if (!p.getResultPercent().matches("^((100)|(\\d{1,2}))$") || p.getResultPercent().equals("")) {
					addFieldError("pruefungenBeans[" + i + "].resultPercent", getMsg(MessageKey.ERR_KEIN_PROZENT));
				}
				if (p.getErgDatum() == null) {
					addFieldError("pruefungenBeans[" + i + "].ergDatum", getMsg(MessageKey.ERR_EMP_DATUM));
				} else {
					if (!p.getDatum().before(p.getErgDatum())) {
						addFieldError("pruefungenBeans[" + i + "].ergDatum", getMsg(MessageKey.ERR_ZU_FRUEH));
					}
					if (p.getErgDatum().getTime() < 1104559200) { // Datum vor 1.1.2005
						p.setErgDatum(null);
						addFieldError("pruefungenBeans[" + i + "].ergDatum", getMsg(MessageKey.ERR_UNGUELTIGES_DATUM));
					}

				}
			}
			i++;
		}
	}

	private void speichereFormulardaten() {
		for (ErgaenzungspruefungsFormBean p : pruefungenBeans) {
			getPruefungService().addErgaenzungsPruefung(p.getStudent(), getSelectedPruefungsfach(), p.getErgDatum(),
					Integer.valueOf(p.getResultPercent()));
		}
	}

	/* Custom Logik Ende */
	/* Actions Start */

	/**
	 * Zeige die zur Eintragung möglichen Ergänzungsprüfungen an
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/**
	 * Validiere (und speichere) die übergebenen Formulardaten der Ergänzungsprüfungen
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		validateForm();

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			speichereFormulardaten();
		}

		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<ErgaenzungspruefungsFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(List<ErgaenzungspruefungsFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
