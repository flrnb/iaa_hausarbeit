package de.nak.iaa.web.view.formbean;

import de.nak.iaa.server.entity.Student;

/**
 * Abstrakte FormBean um diese vergleichbar zu machen und überall den zugehörigen Studenten zur
 * Verfügung zu haben
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
public abstract class AbstractFormBean implements Comparable<AbstractFormBean> {

	public Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public int compareTo(AbstractFormBean o) {
		return this.getStudent().getName().compareTo(o.getStudent().getName());
	}

}
