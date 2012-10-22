package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.web.view.formbean.ErgaenzungspruefungsFormBean;

@SuppressWarnings("serial")
public class ErgaenzungspruefungenAction extends AbstractFormAction implements
		SessionAware, ParameterAware, Preparable {

	private List<ErgaenzungspruefungsFormBean> pruefungenBeans;

	@Override
	public void validate() {
	}

	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<ErgaenzungspruefungsFormBean>());

		for (Student student : getStudentService().getAllStudenten(
				(Manipel) getSession().get("selectedManipel"))) {
			if (student == null)
				continue;
			else {
				// TODO logik einbauen, damit der service benutzt wird

				// getPruefungenBeans().add(
				// new PruefungsleistungFormBean(student.getMatrikelNr(),
				// student.getVorname() + " " + student.getName(),
				// , null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	public String save() throws Exception {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		int i = 0;
		for (ErgaenzungspruefungsFormBean p : pruefungenBeans) {

			// TODO hier validieren
			if (p.getResultPercent().matches("\n{1,2}|100")) {
				addFieldError("pruefungenBeans[" + i + "].note",
						"Keine gültige Note");
			}
			System.out.println(p.getResultPercent());
			// TODO process the form and save the noten
			i++;
		}

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String show() throws Exception {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}
		return Action.SUCCESS;
	}

	public List<ErgaenzungspruefungsFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(
			List<ErgaenzungspruefungsFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
