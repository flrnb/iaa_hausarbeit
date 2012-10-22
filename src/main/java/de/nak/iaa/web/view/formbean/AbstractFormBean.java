package de.nak.iaa.web.view.formbean;

public abstract class AbstractFormBean implements Comparable<AbstractFormBean> {

	protected int matrikelNummer;
	protected String name;

	public int getMatrikelNummer() {
		return matrikelNummer;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(AbstractFormBean o) {
		return this.getName().compareTo(o.getName());
	}

}
