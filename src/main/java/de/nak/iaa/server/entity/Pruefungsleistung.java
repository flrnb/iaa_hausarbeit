package de.nak.iaa.server.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

/**
 * Entity einer durch einen Studenten abgelegten Prüfungsleistung. Sie gehört zu
 * einer Prüfung und einem Studenten. Entsprechend ist auch ein Versuch
 * zugeordnet. Sie kann eine Ergänzungsprüfung enthalten.
 * 
 * @author Ronny Bräunlich
 * 
 */
@Audited
@Entity
public class Pruefungsleistung {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private Versuch versuch;

	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "ERGAENZUNGSPRUEFUNG_ID")
	private ErgaenzungsPruefung ergaenzungsPruefung;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "PRUEFUNG_ID", nullable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Pruefung pruefung;

	@Enumerated(EnumType.STRING)
	private Note note;

	@ManyToOne(optional = false)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Student student;

	public Pruefungsleistung() {
	}

	public Pruefungsleistung(Versuch versuch, Pruefung pruefung, Note note,
			Student student) {
		super();
		this.versuch = versuch;
		this.pruefung = pruefung;
		this.note = note;
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Versuch getVersuch() {
		return versuch;
	}

	public void setVersuch(Versuch versuch) {
		this.versuch = versuch;
	}

	public ErgaenzungsPruefung getErgaenzungsPruefung() {
		return ergaenzungsPruefung;
	}

	public void setErgaenzungsPruefung(ErgaenzungsPruefung ergaenzungsPruefung) {
		this.ergaenzungsPruefung = ergaenzungsPruefung;
	}

	public Pruefung getPruefung() {
		return pruefung;
	}

	public void setPruefung(Pruefung pruefung) {
		this.pruefung = pruefung;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result
				+ ((pruefung == null) ? 0 : pruefung.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		result = prime * result + ((versuch == null) ? 0 : versuch.hashCode());
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
		Pruefungsleistung other = (Pruefungsleistung) obj;
		if (note != other.note)
			return false;
		if (pruefung == null) {
			if (other.pruefung != null)
				return false;
		} else if (!pruefung.equals(other.pruefung))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (versuch != other.versuch)
			return false;
		return true;
	}

}
