package de.nak.iaa.server.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import de.nak.iaa.server.fachwert.Note;

/**
 * Entity, welche eine Ergänzungsprüfung darstellt. Eine Ergänzungsprüfung
 * gehört immer zu einer Prüfungsleistung und kann dadurch die Note noch
 * verbessern.
 * 
 * @author Ronny Bräunlich
 * 
 */
@Audited
@Entity
public class ErgaenzungsPruefung {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private Note note;
	@Temporal(TemporalType.DATE)
	private Date datum;

	public ErgaenzungsPruefung() {
	}

	public ErgaenzungsPruefung(Note note, Date datum) {
		super();
		this.note = note;
		this.datum = datum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Ergänzungsprüfung vom " + new SimpleDateFormat().format(datum)
				+ " mit Note " + note;
	}

}
