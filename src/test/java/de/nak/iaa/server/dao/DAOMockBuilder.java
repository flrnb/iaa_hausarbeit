package de.nak.iaa.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;

/**
 * Generischer Mockbuilder für DAOs <br/>
 * Wird zum Testen der Serviceklassen benötigt.
 * 
 * @author flrnb
 */
public class DAOMockBuilder<E, T extends GenericDAO<E, Long>> {

	private final T mock;

	private final List<E> entities = new ArrayList<E>();

	public static <E, T extends GenericDAO<E, Long>> DAOMockBuilder<E, T> forClass(Class<T> clazz) {
		return new DAOMockBuilder<E, T>(EasyMock.createNiceMock(clazz));
	}

	private DAOMockBuilder(T mock) {
		this.mock = mock;
	}

	public DAOMockBuilder<E, T> addEntities(List<E> entities) {
		this.entities.addAll(entities);
		return this;
	}

	public DAOMockBuilder<E, T> addEntities(E... entities) {
		return this.addEntities(Arrays.asList(entities));
	}

	public T build() {
		EasyMock.expect(mock.findAll()).andReturn(entities).anyTimes();
		EasyMock.replay(mock);
		return mock;
	}

	public static void main(String[] args) {
		DAOMockBuilder.forClass(PruefungsleistungDAO.class);
	}

}
