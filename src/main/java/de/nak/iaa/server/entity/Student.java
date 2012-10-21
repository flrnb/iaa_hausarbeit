package de.nak.iaa.server.entity;

public class Student extends Person {

	private int matrikelNr;

	public Student(int matrikelNr, String name, String vorname) {
		setMatrikelNr(matrikelNr);
		setName(name);
		setVorname(vorname);
	}

	public int getMatrikelNr() {
		return matrikelNr;
	}

	public void setMatrikelNr(int matrikelNr) {
		this.matrikelNr = matrikelNr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matrikelNr;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (matrikelNr != other.matrikelNr)
			return false;
		return true;
	}

}
