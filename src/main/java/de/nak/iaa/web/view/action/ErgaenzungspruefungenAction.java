package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.entity.Student;
import de.nak.iaa.web.view.formbean.ErgaenzungspruefungsFormBean;

@SuppressWarnings("serial")
public class ErgaenzungspruefungenAction extends AbstractFormAction implements
		SessionAware, ParameterAware, Preparable {

	private List<ErgaenzungspruefungsFormBean> pruefungenBeans;

	/* Logik Start */

	@Override
	public void validate() {
	}

	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<ErgaenzungspruefungsFormBean>());

		for (Entry<Student, Date> ergaenzungspruefung : getPruefungService()
				.getAllErgaenzungsPruefungsStudenten(getSelectedManipel(),
						getSelectedPruefungsfach()).entrySet()) {
			if (ergaenzungspruefung == null)
				continue;
			else {

				// TODO logik einbauen, damit der service benutzt wird

				getPruefungenBeans()
						.add(new ErgaenzungspruefungsFormBean(
								ergaenzungspruefung.getKey(),
								ergaenzungspruefung.getValue(), null));
			}
		}

		Collections.sort(getPruefungenBeans());
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
			if (!p.getResultPercent().matches("\n{1,2}|100")) {
				addFieldError("pruefungenBeans[" + i + "].note",
						"Keine gültige Note");
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
