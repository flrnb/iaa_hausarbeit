package de.nak.iaa.server.entity;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.server.fachwert.Versuch;

@Audited
@AuditTable(value = "pruefungsleistung_rev")
@Entity
public class Pruefungsleistung {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private Versuch versuch;
	@Temporal(TemporalType.DATE)
	private Date pruefungsDatum;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ERGAENZUNGSPRUEFUNG_ID")
	private ErgaenzungsPruefung ergaenzungsPruefung;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "PRUEFUNG_ID", nullable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Pruefung pruefung;

	@Enumerated(EnumType.STRING)
	private Note note;

	public Pruefungsleistung() {
	}

	public Pruefungsleistung(Versuch versuch, Date pruefungsDatum,
			Pruefung pruefung, Note note) {
		super();
		this.versuch = versuch;
		this.pruefungsDatum = pruefungsDatum;
		this.pruefung = pruefung;
		this.note = note;
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

	public Date getPruefungsDatum() {
		return pruefungsDatum;
	}

	public void setPruefungsDatum(Date pruefungsDatum) {
		this.pruefungsDatum = pruefungsDatum;
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

}
