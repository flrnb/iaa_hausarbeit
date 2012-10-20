package de.nak.iaa.server.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.nak.iaa.server.fachwert.Studienrichtung;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "JAHRGANG",
		"STUDIENRICHTUNG" }))
public class Manipel {

	private int jahrgang;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Studienrichtung studienrichtung;

	public Manipel() {
	}

	public Manipel(int jahrgang, Studienrichtung studienrichtung) {
		this.jahrgang = jahrgang;
		this.studienrichtung = studienrichtung;
	}

	public int getJahrgang() {
		return jahrgang;
	}

	public void setJahrgang(int jahrgang) {
		this.jahrgang = jahrgang;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Studienrichtung getStudienrichtung() {
		return studienrichtung;
	}

	public void setStudienrichtung(Studienrichtung studienrichtung) {
		this.studienrichtung = studienrichtung;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jahrgang;
		result = prime * result
				+ ((studienrichtung == null) ? 0 : studienrichtung.hashCode());
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
		Manipel other = (Manipel) obj;
		if (jahrgang != other.jahrgang)
			return false;
		if (studienrichtung != other.studienrichtung)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ((String.valueOf(jahrgang).length() == 1) ? "0" + jahrgang
				: jahrgang) + " " + studienrichtung.toString();
	}
}
