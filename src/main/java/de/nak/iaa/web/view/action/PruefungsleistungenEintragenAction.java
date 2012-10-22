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
import de.nak.iaa.web.view.formbean.PruefungsleistungFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenEintragenAction extends AbstractAction
		implements SessionAware, ParameterAware, Preparable {

	private List<PruefungsleistungFormBean> pruefungenBeans;

	/* Logik Start */

	@Override
	public void validate() {
	}

	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungFormBean>());

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

	/* Logik Ende */
	/* Actions Start */

	public String save() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		for (PruefungsleistungFormBean p : pruefungenBeans) {
			System.out.println(p.getNote()); // TODO process the form and save
												// the noten
		}
		// TODO show the protokoll
		return Action.SUCCESS;
	}

	// TODO fehlerbehandlung hinzufügen
	public String show() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<PruefungsleistungFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(
			List<PruefungsleistungFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
