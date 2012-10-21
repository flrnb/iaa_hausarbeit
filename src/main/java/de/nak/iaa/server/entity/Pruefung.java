package de.nak.iaa.server.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pruefung {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date datum;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRUEFUNGSFACH_ID", nullable = false)
	private Pruefungsfach pruefungsfach;

	// @ManyToOne
	// @JoinColumn
	// private Dozent pruefer;

	public Pruefung() {
	}

	public Pruefung(Date datum, Pruefungsfach pruefungsfach /* , Dozent pruefer */) {
		super();
		this.datum = datum;
		this.pruefungsfach = pruefungsfach;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Pruefungsfach getPruefungsfach() {
		return pruefungsfach;
	}

	public void setPruefungsfach(Pruefungsfach pruefungsfach) {
		this.pruefungsfach = pruefungsfach;
	}

}