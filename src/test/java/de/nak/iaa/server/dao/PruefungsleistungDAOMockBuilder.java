package de.nak.iaa.server.dao;

import java.util.ArrayList;

import org.easymock.EasyMock;

import de.nak.iaa.server.entity.Pruefungsfach;
import de.nak.iaa.server.entity.Pruefungsleistung;
import de.nak.iaa.server.entity.Student;
import de.nak.iaa.server.fachwert.Versuch;

public class PruefungsleistungDAOMockBuilder extends DAOMockBuilder<Pruefungsleistung, PruefungsleistungDAO> {

	public PruefungsleistungDAOMockBuilder() {
		super(PruefungsleistungDAO.class);
	}

	@Override
	public PruefungsleistungDAO build() {
		EasyMock.expect(
				getMock().getVersuchFallsVorhanden(EasyMock.anyObject(Student.class),
						EasyMock.anyObject(Pruefungsfach.class), EasyMock.anyObject(Versuch.class)))
				.andReturn(new ArrayList<Pruefungsleistung>()).anyTimes();
		return super.build();
	}

}
