package de.nak.iaa.server.entity;

import java.text.DateFormat;
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

/**
 * Entity für eine Prüfung. Eine Prüfung wird von einem Prüfer an einem
 * bestimmten Datum festgelegt und in einem entsprechenden Prüfungsfach
 * abgelegt.
 * 
 * @author Ronny Bräunlich
 * 
 */
@Entity
public class Pruefung implements Comparable<Pruefung> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date datum;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PRUEFUNGSFACH_ID", nullable = false)
	private Pruefungsfach pruefungsfach;

	@ManyToOne(optional = false)
	@JoinColumn(name = "DOZENT_ID", nullable = false)
	private Dozent pruefer;

	public Pruefung() {
	}

	public Pruefung(Date datum, Pruefungsfach pruefungsfach, Dozent pruefer) {
		super();
		this.datum = datum;
		this.pruefungsfach = pruefungsfach;
		this.pruefer = pruefer;
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

	public Dozent getPruefer() {
		return pruefer;
	}

	public void setPruefer(Dozent pruefer) {
		this.pruefer = pruefer;
	}

	@Override
	public String toString() {
		return DateFormat.getDateInstance().format(getDatum());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((pruefer == null) ? 0 : pruefer.hashCode());
		result = prime * result
				+ ((pruefungsfach == null) ? 0 : pruefungsfach.hashCode());
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
		Pruefung other = (Pruefung) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (pruefer == null) {
			if (other.pruefer != null)
				return false;
		} else if (!pruefer.equals(other.pruefer))
			return false;
		if (pruefungsfach == null) {
			if (other.pruefungsfach != null)
				return false;
		} else if (!pruefungsfach.equals(other.pruefungsfach))
			return false;
		return true;
	}

	@Override
	public int compareTo(Pruefung o) {
		if (getDatum().equals(o.getDatum()))
			return 0;
		else if (getDatum().after(o.getDatum()))
			return 1;
		return -1;
	}

}