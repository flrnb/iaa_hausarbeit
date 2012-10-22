package de.nak.iaa.server.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Dozent extends Person {

	public Dozent(String name, String vorname) {
		setName(name);
		setVorname(vorname);

	}

}
