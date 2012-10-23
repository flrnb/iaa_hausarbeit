package de.nak.iaa.server.entity;

import javax.persistence.Entity;

@Entity
public class Dozent extends Person {

	public Dozent() {

	}

	public Dozent(String name, String vorname) {
		super(name, vorname);
	}

}
