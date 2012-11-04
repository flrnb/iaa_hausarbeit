package de.nak.iaa.web.view.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;

@SuppressWarnings("serial")
public class ShowOverview extends AbstractAction {

	private List<Pruefungsfach> faecher;
	private List<Student> studenten;
	private Map<Pruefungsfach, Map<Student, Note>> ergebnisse;

	/* Actions Start */

	public String show() {
		if (isManipelSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		setFaecher(getPruefungService().getAllPruefungsfaecher(
				getSelectedManipel()));
		setStudenten(getStudentService().getAllStudenten(getSelectedManipel()));

		ergebnisse = new HashMap<Pruefungsfach, Map<Student, Note>>();

		for (Pruefungsfach pf : getFaecher()) {
			Map<Student, Note> stpMap = new TreeMap<Student, Note>();
			for (Student st : getStudenten()) {
				Optional<Note> note = getPruefungService().getAktuelleNote(st,
						pf);
				stpMap.put(st, (note.isPresent()) ? note.get() : null);
			}
			ergebnisse.put(pf, stpMap);
		}

		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<Pruefungsfach> getFaecher() {
		return faecher;
	}

	public void setFaecher(List<Pruefungsfach> faecher) {
		this.faecher = faecher;
	}

	public Map<Pruefungsfach, Map<Student, Note>> getErgebnisse() {
		return ergebnisse;
	}

	public void setErgebnisse(Map<Pruefungsfach, Map<Student, Note>> ergebnisse) {
		this.ergebnisse = ergebnisse;
	}

	public List<Student> getStudenten() {
		return studenten;
	}

	public void setStudenten(List<Student> studenten) {
		this.studenten = studenten;
	}
}
