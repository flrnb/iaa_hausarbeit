package de.nak.iaa.web.view.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.javatuples.Triplet;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.Action;

import de.nak.iaa.server.business.IllegalUpdateException;
import de.nak.iaa.server.business.IllegalUpdateException.IllegalPruefungsleistungException;
import de.nak.iaa.server.entity.Pruefung;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.entity.Protokollzeile;
import de.nak.iaa.web.view.formbean.PruefungsleistungFormBean;

/**
 * Action zum Anlegen neuer Prüfungsleistungen
 * 
 * @author Christopher Biel
 * 
 */
public class PruefungsleistungenEintragenAction extends AbstractFormAction {

	private static final long serialVersionUID = 1L;

	private List<PruefungsleistungFormBean> pruefungenBeans;

	private Map<Student, Protokollzeile> protokoll;
	private boolean hasErrors = false;
	private boolean protokollHasErrors;

	private Date dateToday;

	/* Custom Logik Start */

	/**
	 * Fülle die pruefungenBeans
	 */
	public void fuellePruefungsBeans() {
		setPruefungenBeans(new ArrayList<PruefungsleistungFormBean>());

		for (Entry<Student, Optional<Pruefungsleistung>> student : getPruefungService().getAllStudentenForPruefung(
				getSelectedPruefung()).entrySet()) {
			if (student == null || !student.getKey().getManipel().equals(getSelectedManipel()))
				continue;
			else {
				StringBuilder alteNote = new StringBuilder();
				if (student.getValue().isPresent()) {
					alteNote.append(student.getValue().get().getNote());
					if (student.getValue().get().getErgaenzungsPruefung() != null) {
						alteNote.append(" > ");
						alteNote.append(student.getValue().get().getErgaenzungsPruefung().getNote());
					}
				}

				getPruefungenBeans().add(new PruefungsleistungFormBean(student.getKey(), alteNote.toString(), null));
			}
		}

		Collections.sort(getPruefungenBeans());
	}

	/**
	 * Validiere die Eingabedaten für die Zeilen, wo mindestens eines der Felder gefüllt ist<br>
	 * Fügt bei einem Problem, dem Feld einen FieldError hinzu
	 */
	private void validateForm() {
		int i = 0;
		for (PruefungsleistungFormBean p : pruefungenBeans) {
			if (p.getNote().contains(",")) {
				addFieldError("pruefungenBeans[" + i + "].note", "Nur \".\" erlaubt");
				// continue;
			}
			if (!p.getNote().equals("") && !p.getNote().contains(",") && !Note.isValid(p.getNote())) {
				addFieldError("pruefungenBeans[" + i + "].note", "Keine gültige Note");
			}
			i++;
		}
	}

	/**
	 * Hole ein persitentes Student Objekt, zu einem lokalen Studenten Objekt
	 * 
	 * @param student
	 * @return
	 */
	private Optional<Student> getPersistentStudentForLocalCopy(Student student) {
		Student st = null;
		for (Student s : getStudentService().getAllStudenten(getSelectedManipel())) {
			if (s.equals(student)) {
				st = s;
				break;
			}
		}
		return Optional.fromNullable(st);
	}

	/**
	 * Hole alle neuen Prüfungsleistungen und trage initiale Nachrichten in das Protokoll ein
	 * 
	 * @return
	 */
	private List<Triplet<Pruefung, Student, Note>> getAlleNeuenPruefungsleistungen() {
		List<Triplet<Pruefung, Student, Note>> leistungen = new ArrayList<Triplet<Pruefung, Student, Note>>();

		for (PruefungsleistungFormBean p : pruefungenBeans) {
			if (p.getNote().equals(""))
				continue;

			Optional<Student> st = getPersistentStudentForLocalCopy(p.getStudent());
			if (!st.isPresent())
				continue;

			leistungen.add(new Triplet<Pruefung, Student, Note>(getSelectedPruefung(), st.get(), Note.getNote(p
					.getNote())));
			getProtokoll().put(
					st.get(),
					new Protokollzeile(Protokollzeile.NACHRICHT, "Note " + p.getNote() + " für "
							+ st.get().getVorname() + " " + st.get().getName() + " erfolgreich eingetragen."));

		}
		return leistungen;
	}

	/* Custom Logik Ende */
	/* Actions Start */

	/**
	 * Zeige die Prüfungsleistungen, die geändert werden können
	 * 
	 * @return
	 */
	public String show() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		fuellePruefungsBeans();
		return Action.SUCCESS;
	}

	/**
	 * Speichere die Prüfungsleistungen und fülle das Protokoll
	 * 
	 * @return
	 */
	public String save() {
		if (isManipelNotSelected()) {
			setTargetUrl(getRequestUrl());
			return NO_MANIPEL_SELECTED;
		}

		validateForm();

		if (getFieldErrors().size() > 0) {
			fuellePruefungsBeans();
			return Action.INPUT;
		} else {
			setDateToday(new Date());
			protokollHasErrors = false;
			setProtokoll(new TreeMap<Student, Protokollzeile>());

			List<Triplet<Pruefung, Student, Note>> leistungen = getAlleNeuenPruefungsleistungen();

			try {
				getPruefungService().addPruefungsleistungen(leistungen);
			} catch (IllegalUpdateException e) {
				setHasErrors(true);
				for (IllegalPruefungsleistungException ipe : e.getNestedExceptions().get()) {
					getProtokoll().put(
							ipe.getStudent(),
							new Protokollzeile(Protokollzeile.FEHLER, "Note für " + ipe.getStudent()
									+ " konnte nicht eingetragen werden (Grund: " + ipe.getMessage() + ")"));
				}
			}
		}

		return Action.SUCCESS;
	}

	/* Actions Ende */
	/* Properties */

	public List<PruefungsleistungFormBean> getPruefungenBeans() {
		return pruefungenBeans;
	}

	public void setPruefungenBeans(List<PruefungsleistungFormBean> pruefungenBeans) {
		this.pruefungenBeans = pruefungenBeans;
	}

	public Map<Student, Protokollzeile> getProtokoll() {
		return protokoll;
	}

	public void setProtokoll(Map<Student, Protokollzeile> protokoll) {
		this.protokoll = protokoll;
	}

	public boolean isProtokollHasErrors() {
		return protokollHasErrors;
	}

	public void setProtokollHasErrors(boolean protokollHasErrors) {
		this.protokollHasErrors = protokollHasErrors;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Date getDateToday() {
		return dateToday;
	}

	public void setDateToday(Date dateToday) {
		this.dateToday = dateToday;
	}
}
