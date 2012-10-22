package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.web.util.DataHelper;
import de.nak.iaa.web.view.formbean.PruefungsleistungFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenEintragenAction extends ActionSupport implements
		SessionAware, ParameterAware, Preparable, ValidationAware {

	private static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	// private List<Pruefungsfach> pruefungsfaecher;
	// private List<Pruefung> pruefungen;
	private Pruefungsfach selectedPruefungsfach;
	private Pruefung selectedPruefung;
	private List<PruefungsleistungFormBean> pruefungenBeans;

	private String pruefungsfach;
	private String pruefung;

	/* Logik Start */

	@Override
	public void prepare() throws Exception {
		setPruefung(DataHelper.stringArrayToString(parameters.get("pruefung")));
		setPruefungsfach(DataHelper.stringArrayToString(parameters
				.get("pruefungsfach")));
	}

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

				List<Pruefungsleistung> pruefungsleistungen = getPruefungService()
						.getAllPruefungsleistungen(selectedPruefungsfach,
								student);

				if (pruefungsleistungen == null
						|| pruefungsleistungen.size() == 0)
					continue;

				StringBuilder alteNoten = new StringBuilder();
				if (pruefungsleistungen.size() > 1) {
					for (Pruefungsleistung leistung : pruefungsleistungen) {
						alteNoten.append(leistung.getNote());
					}
				}

				getPruefungenBeans().add(
						new PruefungsleistungFormBean(pruefungsleistungen.get(
								pruefungsleistungen.size() - 1).getId(),
								student.getMatrikelNr(), student.getVorname()
										+ " " + student.getName(), alteNoten
										.toString(), null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/* Logik Ende */
	/* Actions Start */

	// public String showSelectModul() {
	// if (!getSession().containsKey("selectedManipel")
	// || getSession().get("selectedManipel") == null
	// || getSession().get("selectedManipel").equals("")) {
	// setTargetUrl(ServletActionContext.getRequest().getRequestURL()
	// .toString());
	// return NO_MANIPEL_SELECTED;
	// }
	// setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(
	// (Manipel) getSession().get("selectedManipel")));
	// return Action.SUCCESS;
	// }
	//
	// public String showSelectPruefung() {
	// if (!getSession().containsKey("selectedManipel")
	// || getSession().get("selectedManipel") == null
	// || getSession().get("selectedManipel").equals("")) {
	// setTargetUrl(ServletActionContext.getRequest().getRequestURL()
	// .toString());
	// return NO_MANIPEL_SELECTED;
	// }
	//
	// if (getParameters().containsKey("pruefungsfach")
	// && !getParameters().get("pruefungsfach").equals("")) {
	// selectedPruefungsfach = getPruefungService().getPruefungsfachById(
	// Long.valueOf(DataHelper.stringArrayToString(getParameters()
	// .get("pruefungsfach"))));
	// }
	//
	// setPruefungen(getPruefungService().getAllPruefung(
	// (Manipel) getSession().get("selectedManipel"),
	// getSelectedPruefungsfach()));
	// return Action.SUCCESS;
	// }

	public String save() {
		for (PruefungsleistungFormBean p : pruefungenBeans) {
			System.out.println(p.getNote()); // TODO process the form and save
												// the noten
		}
		// TODO show the history
		return Action.SUCCESS;
	}

	// TODO fehlerbehandlung hinzufügen
	public String show() {
		if (getParameters().containsKey("pruefungsfach")
				&& !getParameters().get("pruefungsfach").equals("")) {

			selectedPruefungsfach = getPruefungService().getPruefungsfachById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters()
							.get("pruefungsfach"))));
		}
		if (getParameters().containsKey("pruefung")
				&& !getParameters().get("pruefung").equals("")) {

			selectedPruefung = getPruefungService().getPruefungById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters()
							.get("pruefung"))));
		}

		fuellePruefungsBeans();

		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Session Management Start */

	private Map<String, Object> session;

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/* Session Management Ende */
	/* Parameter Management Start */

	private Map<String, String[]> parameters;

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	/* Parameter Management Ende */
	/* Service Management Start */
	private StudentService studentService;

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	private PruefungService pruefungService;

	public PruefungService getPruefungService() {
		return pruefungService;
	}

	public void setPruefungService(PruefungService pruefungService) {
		this.pruefungService = pruefungService;
	}

	/* Service Management Ende */
	/* Ziel Url (für Fehlerfall) Start */

	private String targetUrl;

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	/* Ziel Url (für Fehlerfall) Ende */
	/* Properties */

	public Pruefungsfach getSelectedPruefungsfach() {
		return selectedPruefungsfach;
	}

	public void setSelectedPruefungsfach(Pruefungsfach selectedPruefungsfach) {
		this.selectedPruefungsfach = selectedPruefungsfach;
	}

	// public List<Pruefungsfach> getPruefungsfaecher() {
	// return pruefungsfaecher;
	// }
	//
	// public void setPruefungsfaecher(List<Pruefungsfach> pruefungsfaecher) {
	// this.pruefungsfaecher = pruefungsfaecher;
	// }

	public List<PruefungsleistungFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(
			List<PruefungsleistungFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}

	// public List<Pruefung> getPruefungen() {
	// return pruefungen;
	// }
	//
	// public void setPruefungen(List<Pruefung> pruefungen) {
	// this.pruefungen = pruefungen;
	// }

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
