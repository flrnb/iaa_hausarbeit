package de.nak.iaa.web.view.action;

import java.util.List;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.web.view.formbean.PruefungsleistungAendernFormBean;

@SuppressWarnings("serial")
public class PruefungsleistungenAendernAction extends AbstractFormAction {

	private List<PruefungsleistungAendernFormBean> pruefungenBeans;

	public String show() {
		return Action.SUCCESS;
	}

	public String save() {
		return Action.SUCCESS;
	}

	/* Properties */

	public List<PruefungsleistungAendernFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(
			List<PruefungsleistungAendernFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}
}
