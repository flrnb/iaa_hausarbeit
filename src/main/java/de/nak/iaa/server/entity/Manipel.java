package de.nak.iaa.server.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "jahrgang",
		"studienrichtung" }))
public class Manipel {

	private int jahrgang;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated
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

}
