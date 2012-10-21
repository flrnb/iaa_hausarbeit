package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.server.business.PruefungService;
import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.web.util.DataHelper;
import de.nak.iaa.web.view.formbean.PruefungsleistungFormBean;

// TODO muss von String modulen auf Pruefungsfach klasse umgestellt werden
@SuppressWarnings("serial")
public class PruefungsleistungenEintragenAction extends ActionSupport implements
		SessionAware, ParameterAware {

	private static final String NO_MANIPEL_SELECTED = "noManipelSelected";

	private Map<String, Object> session;
	private Map<String, String[]> parameters;
	private List<Pruefungsfach> pruefungsfaecher;
	private Pruefungsfach selectedPruefungsfach;
	private List<PruefungsleistungFormBean> pruefungen;

	// entweder ist (ungefähr) das der rückgabe wert des service für
	// prüfungsleistungen
	private Map<String, List<PruefungsleistungFormBean>> pruefungsServiceReturn;

	// oder dies hier, sodass ich dem service eine modul-id oder sowas mitgebe

	// wahrscheinlich wäre die erste variante besser, weil das zweite auch von
	// mir zusammengebaut wird, allerdings muss die pruefungsleisterungsbean so
	// aufgebaut sein, damit ich problemlos ind er view auf die daten zugreifen
	// kann

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String showSelectModul() {
		if (!getSession().containsKey("selectedManipel")
				|| getSession().get("selectedManipel") == null
				|| getSession().get("selectedManipel").equals("")) {
			setTargetUrl(ServletActionContext.getRequest().getRequestURL()
					.toString());
			return NO_MANIPEL_SELECTED;
		}
		setPruefungsfaecher(getPruefungService().getPruefungsfaecherForManipel(
				(Manipel) getSession().get("selectedManipel")));
		return Action.SUCCESS;
	}

	public String save() {
		return Action.SUCCESS;
	}

	public String show() {
		if (getParameters().containsKey("pruefungsfach")
				&& !getParameters().get("pruefungsfach").equals("")) {

			System.out
					.println(Long.valueOf(DataHelper
							.stringArrayToString(getParameters().get(
									"pruefungsfach"))));
			selectedPruefungsfach = getPruefungService().getPruefungsfachById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters()
							.get("pruefungsfach"))));
		}

		pruefungen = new ArrayList<PruefungsleistungFormBean>();

		for (Student student : getStudentService().getAllStudenten(
				(Manipel) getSession().get("selectedManipel"))) {
			if (student == null)
				continue;
			else {

				List<Pruefungsleistung> pruefungsleistungen = getPruefungService()
						.getPruefungsleistungenForStudentAndPruefungsfach(
								student, selectedPruefungsfach);

				if (pruefungsleistungen == null)
					continue;

				pruefungsleistungen.get(pruefungsleistungen.size() - 1).getId();
				StringBuilder alteNoten = new StringBuilder();
				if (pruefungsleistungen.size() > 1) {
					for (Pruefungsleistung leistung : pruefungsleistungen) {
						alteNoten.append(leistung.getNote());
					}
				}

				pruefungen.add(new PruefungsleistungFormBean(
						pruefungsleistungen.get(pruefungsleistungen.size() - 1)
								.getId(), student.getMatrikelNr(), student
								.getVorname() + " " + student.getName(),
						alteNoten.toString(), null));
			}

		}
		return Action.SUCCESS;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public List<PruefungsleistungFormBean> getPruefungen() {
		return pruefungen;
	}

	public void setPruefungen(List<PruefungsleistungFormBean> pruefungen) {
		this.pruefungen = pruefungen;
	}

	public Map<String, List<PruefungsleistungFormBean>> getPruefungsServiceReturn() {
		return pruefungsServiceReturn;
	}

	public void setPruefungsServiceReturn(
			Map<String, List<PruefungsleistungFormBean>> pruefungsServiceReturn) {
		this.pruefungsServiceReturn = pruefungsServiceReturn;
	}

	public Pruefungsfach getSelectedPruefungsfach() {
		return selectedPruefungsfach;
	}

	public void setSelectedPruefungsfach(Pruefungsfach selectedPruefungsfach) {
		this.selectedPruefungsfach = selectedPruefungsfach;
	}

	public List<Pruefungsfach> getPruefungsfaecher() {
		return pruefungsfaecher;
	}

	public void setPruefungsfaecher(List<Pruefungsfach> pruefungsfaecher) {
		this.pruefungsfaecher = pruefungsfaecher;
	}

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
}
