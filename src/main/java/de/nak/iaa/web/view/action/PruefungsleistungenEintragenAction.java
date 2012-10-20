package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.web.util.DataHelper;
import de.nak.iaa.web.view.formbean.PruefungsleistungsBean;

// TODO muss von String modulen auf Pruefungsfach klasse umgestellt werden
@SuppressWarnings("serial")
public class PruefungsleistungenEintragenAction extends ActionSupport implements
		SessionAware, ParameterAware {

	private Map<String, Object> session;
	private Map<String, String[]> parameters;
	private List<String> module;
	private String selectedModule;

	// entweder ist (ungefähr) das der rückgabe wert des service für
	// prüfungsleistungen
	private Map<String, List<PruefungsleistungsBean>> pruefungsServiceReturn;
	// oder dies hier, sodass ich dem service eine modul-id oder sowas mitgebe
	private List<PruefungsleistungsBean> pruefungen;

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

	public PruefungsleistungenEintragenAction() {

		// TODO Beispieldaten entfernen
		setModule(new ArrayList<String>());
		getModule().add("123");
		getModule().add("124");
		getModule().add("125");

		setPruefungsServiceReturn(new HashMap<String, List<PruefungsleistungsBean>>());
		ArrayList<PruefungsleistungsBean> l1 = new ArrayList<PruefungsleistungsBean>();
		ArrayList<PruefungsleistungsBean> l2 = new ArrayList<PruefungsleistungsBean>();
		ArrayList<PruefungsleistungsBean> l3 = new ArrayList<PruefungsleistungsBean>();

		l1.add(new PruefungsleistungsBean("1", "123", "cb", "5", null));
		l2.add(new PruefungsleistungsBean("1", "124", "cb", "5", null));
		l3.add(new PruefungsleistungsBean("1", "125", "cb", "5", null));

		getPruefungsServiceReturn().put("123", l1);
		getPruefungsServiceReturn().put("124", l2);
		getPruefungsServiceReturn().put("125", l3);
	}

	public String showSelectModul() {
		return Action.SUCCESS;
	}

	public String save() {
		return Action.SUCCESS;
	}

	public String show() {
		if (getParameters().containsKey("module")
				&& !getParameters().get("module").equals("")) {
			System.out.println(getParameters().get("module"));
			selectedModule = DataHelper.stringArrayToString(getParameters()
					.get("module"));
		}
		setPruefungen(getPruefungsServiceReturn().get(selectedModule));
		return Action.SUCCESS;
	}

	public List<String> getModule() {
		return module;
	}

	public void setModule(List<String> module) {
		this.module = module;
	}

	public String getSelectedModul() {
		return selectedModule;
	}

	public void setSelectedModul(String selectedModul) {
		this.selectedModule = selectedModul;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public List<PruefungsleistungsBean> getPruefungen() {
		return pruefungen;
	}

	public void setPruefungen(List<PruefungsleistungsBean> pruefungen) {
		this.pruefungen = pruefungen;
	}

	public Map<String, List<PruefungsleistungsBean>> getPruefungsServiceReturn() {
		return pruefungsServiceReturn;
	}

	public void setPruefungsServiceReturn(
			Map<String, List<PruefungsleistungsBean>> pruefungsServiceReturn) {
		this.pruefungsServiceReturn = pruefungsServiceReturn;
	}
}
