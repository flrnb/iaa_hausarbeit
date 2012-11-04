package de.nak.iaa.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "MATRIKEL_NR" }))
public class Student extends Person implements Comparable<Student> {

	@Column(name = "MATRIKEL_NR")
	private int matrikelNr;

	@OneToOne
	@JoinColumn(nullable = false, name = "MANIPEL_ID")
	private Manipel manipel;

	public Student() {
	}

	public Student(int matrikelNr, Manipel manipel, String name, String vorname) {
		super(name, vorname);
		this.matrikelNr = matrikelNr;
		this.manipel = manipel;
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

	public Manipel getManipel() {
		return manipel;
	}

	public void setManipel(Manipel manipel) {
		this.manipel = manipel;
	}

	@Override
	public int compareTo(Student student) {
		return this.getName().compareTo(student.getName());
	}
}
