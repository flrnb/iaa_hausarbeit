package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.web.view.formbean.ErgaenzungspruefungsFormBean;

@SuppressWarnings("serial")
public class ErgaenzungspruefungenAction extends AbstractFormAction {

	private List<ErgaenzungspruefungsFormBean> pruefungenBeans;

	/* Custom Logik Start */

	/**
	 * Fülle die pruefungenBeans
	 */
	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<ErgaenzungspruefungsFormBean>());

		for (Entry<Student, Date> ergaenzungspruefung : getPruefungService()
				.getAllErgaenzungsPruefungsStudenten(getSelectedManipel(),
						getSelectedPruefungsfach()).entrySet()) {
			if (ergaenzungspruefung == null)
				continue;
			else {
				getPruefungenBeans()
						.add(new ErgaenzungspruefungsFormBean(
								ergaenzungspruefung.getKey(),
								ergaenzungspruefung.getValue(), null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/* Custom Logik Ende */
	/* Logik Start */

	@Override
	public void validate() {
	}

	/* Logik Ende */
	/* Actions Start */

	public String save() throws Exception {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		int i = 0;
		for (ErgaenzungspruefungsFormBean p : pruefungenBeans) {
			// TODO hier validieren
			if (p.getResultPercent().isEmpty()
					&& (p.getErgDatum() == null || p.getErgDatum().equals("")))
				continue;

			if (!p.getResultPercent().matches("^((100)|(\\d{1,2}))$")) {
				addFieldError("pruefungenBeans[" + i + "].resultPercent",
						"Keine gültige Prozentzahl");
			}
			if (p.getErgDatum() == null || p.getErgDatum().equals("")) {
				addFieldError("pruefungenBeans[" + i + "].ergDatum",
						"Kein Datum angegeben");
			}
			i++;
		}

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			for (ErgaenzungspruefungsFormBean p : pruefungenBeans) {
				getPruefungService().addErgaenzungsPruefung(p.getStudent(),
						getSelectedPruefungsfach(), p.getErgDatum(),
						Integer.valueOf(p.getResultPercent()));
			}
		}

		return Action.SUCCESS;
	}

	public String show() throws Exception {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}
		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<ErgaenzungspruefungsFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(
			List<ErgaenzungspruefungsFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
