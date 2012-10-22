package de.nak.iaa.web.view.formbean;

public abstract class AbstractFormBean implements Comparable<AbstractFormBean> {

	public Integer matrikelNummer;
	public String name;

	public void setMatrikelNummer(Integer matrikelNummer) {
		this.matrikelNummer = matrikelNummer;
	}

	public void setName(String name) {
		this.name = name;
	}

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
