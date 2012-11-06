package de.nak.iaa.web.view.action;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Student;

public class ShowHistoryAction extends AbstractAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private List<Pruefungsfach> pruefungsfaecher;
	private List<Student> studenten;
	private Long formStudent;
	private Long formPruefungsfach;
	private Student selectedStudent;
	private Pruefungsfach selectedPruefungsfach;

	@Override
	public void prepare() throws Exception {
		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(getSelectedManipel()));
		setStudenten(getStudentService().getAllStudenten(getSelectedManipel()));
	}

	public String select() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		return Action.SUCCESS;
	}

	public String show() {
		if (isManipelNotSelected()) {
			setTargetUrl("/");
			return NO_MANIPEL_SELECTED;
		}

		return Action.SUCCESS;
	}

	public List<Pruefungsfach> getPruefungsfaecher() {
		return pruefungsfaecher;
	}

	public void setPruefungsfaecher(List<Pruefungsfach> pruefungsfaecher) {
		this.pruefungsfaecher = pruefungsfaecher;
	}

	public List<Student> getStudenten() {
		return studenten;
	}

	public void setStudenten(List<Student> studenten) {
		this.studenten = studenten;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public Pruefungsfach getSelectedPruefungsfach() {
		return selectedPruefungsfach;
	}

	public void setSelectedPruefungsfach(Pruefungsfach selectedPruefungsfach) {
		this.selectedPruefungsfach = selectedPruefungsfach;
	}

	public Long getFormStudent() {
		return formStudent;
	}

	public void setFormStudent(Long formStudent) {
		this.formStudent = formStudent;
	}

	public Long getFormPruefungsfach() {
		return formPruefungsfach;
	}

	public void setFormPruefungsfach(Long formPruefungsfach) {
		this.formPruefungsfach = formPruefungsfach;
	}

}
