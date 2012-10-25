package de.nak.iaa.server.entity;

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

@Audited
@Entity
public class Pruefungsleistung {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private Versuch versuch;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ERGAENZUNGSPRUEFUNG_ID")
	private ErgaenzungsPruefung ergaenzungsPruefung;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "PRUEFUNG_ID", nullable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Pruefung pruefung;

	@Enumerated(EnumType.STRING)
	private Note note;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
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

}
