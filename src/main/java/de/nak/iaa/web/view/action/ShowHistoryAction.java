package de.nak.iaa.web.view.action;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import de.nak.iaa.server.business.PruefungsleistungHistoryEntry;
import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Versuch;
import de.nak.iaa.web.util.DataHelper;

public class ShowHistoryAction extends AbstractAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private List<Pruefungsfach> pruefungsfaecher;
	private List<Student> studenten;
	private String formStudent;
	private String formPruefungsfach;
	private Student selectedStudent;
	private Pruefungsfach selectedPruefungsfach;

	private Map<Versuch, SortedSet<PruefungsleistungHistoryEntry>> history;

	@Override
	public void prepare() throws Exception {
		setPruefungsfaecher(getPruefungService().getAllPruefungsfaecher(getSelectedManipel()));
		setStudenten(getStudentService().getAllStudenten(getSelectedManipel()));
	}

	/**
	 * Validiere die Eingabedaten und gebe zurück, ob ein Fehler aufgetreten ist <br>
	 * Fügt bei einem Problem, dem Feld einen FieldError hinzu
	 * 
	 * @return
	 */
	private boolean validateForm() {
		boolean hasError = false;
		if (getFormStudent() == null || getFormStudent().equals("")) {
			addActionMessage("Beide Felder müssen ausgewählt sein!");
			return true;
		}
		if (getFormPruefungsfach() == null || getFormPruefungsfach().equals("")) {
			addActionMessage("Beide Felder müssen ausgewählt sein!");
			return true;
		}
		return hasError;
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
			setTargetUrl("../history/select");
			return NO_MANIPEL_SELECTED;
		}

		if (!validateForm()) {
			Optional<Pruefungsfach> fach = getPruefungService().getPruefungsfachById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters().get("formPruefungsfachKey"))));

			if (fach.isPresent()) {
				setSelectedPruefungsfach(fach.get());
			} else {
				addActionError("Prüfungsfach nicht bekannt");
				return Action.INPUT;
			}

			Optional<Student> student = getStudentService().getStudentById(
					Long.valueOf(DataHelper.stringArrayToString(getParameters().get("formStudentKey"))));

			if (student.isPresent()) {
				setSelectedStudent(student.get());
			} else {
				addActionError("Student nicht bekannt");
				return Action.INPUT;
			}

			setHistory(getPruefungService().getPruefungsleistungHistorie(getSelectedStudent(),
					getSelectedPruefungsfach()));
		} else {
			return Action.INPUT;
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

	public String getFormStudent() {
		return formStudent;
	}

	public void setFormStudent(String formStudent) {
		this.formStudent = formStudent;
	}

	public String getFormPruefungsfach() {
		return formPruefungsfach;
	}

	public void setFormPruefungsfach(String formPruefungsfach) {
		this.formPruefungsfach = formPruefungsfach;
	}

	public Map<Versuch, SortedSet<PruefungsleistungHistoryEntry>> getHistory() {
		return history;
	}

	public void setHistory(Map<Versuch, SortedSet<PruefungsleistungHistoryEntry>> history) {
		this.history = history;
	}

}
