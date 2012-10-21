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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

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

	public Pruefungsleistung() {
	}

	public Pruefungsleistung(Versuch versuch, Date pruefungsDatum) {
		super();
		this.versuch = versuch;
		this.pruefungsDatum = pruefungsDatum;
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

	public Note getNote() {
		// TODO implement
		return null;
	}
}
