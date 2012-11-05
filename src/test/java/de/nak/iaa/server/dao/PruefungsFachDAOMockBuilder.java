package de.nak.iaa.server.dao;

import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IAnswer;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.nak.iaa.server.entity.Manipel;
import de.nak.iaa.server.entity.Pruefungsfach;

public class PruefungsFachDAOMockBuilder extends DAOMockBuilder<Pruefungsfach, PruefungsfachDAO> {

	public PruefungsFachDAOMockBuilder() {
		super(PruefungsfachDAO.class);
	}

	@Override
	public PruefungsfachDAO build() {
		EasyMock.expect(getMock().findePruefungsfaecherFuerManipel(EasyMock.anyObject(Manipel.class)))
				.andAnswer(new IAnswer<List<Pruefungsfach>>() {
					@Override
					public List<Pruefungsfach> answer() throws Throwable {
						return ImmutableList.copyOf(Iterables.filter(getEntities(), new Predicate<Pruefungsfach>() {
							@Override
							public boolean apply(Pruefungsfach input) {
								return input.getManipel().equals(EasyMock.getCurrentArguments()[0]);
							}
						}));
					}
				}).anyTimes();
		return super.build();
	}

}
