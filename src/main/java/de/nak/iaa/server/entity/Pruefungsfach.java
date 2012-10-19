package de.nak.iaa.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "TITEL",
		"MANIPEL_ID" }))
public class Pruefungsfach {
	private String titel;
	private String beschreibung;

	@ManyToOne
	@JoinColumn(name = "MANIPEL_ID")
	private Manipel manipel;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Pruefungsfach() {
	}

	public Pruefungsfach(String titel, String beschreibung, Manipel manipel) {
		super();
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.manipel = manipel;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Manipel getManipel() {
		return manipel;
	}

	public void setManipel(Manipel manipel) {
		this.manipel = manipel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((manipel == null) ? 0 : manipel.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
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
		Pruefungsfach other = (Pruefungsfach) obj;
		if (manipel == null) {
			if (other.manipel != null)
				return false;
		} else if (!manipel.equals(other.manipel))
			return false;
		if (titel == null) {
			if (other.titel != null)
				return false;
		} else if (!titel.equals(other.titel))
			return false;
		return true;
	}

}
