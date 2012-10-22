package de.nak.iaa.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.beanutils.BeanUtils;
import org.easymock.EasyMock;
import org.easymock.IAnswer;

import de.nak.iaa.server.entity.Pruefungsleistung;

/**
 * Generischer Mockbuilder für DAOs <br/>
 * Wird zum Testen der Serviceklassen benötigt.
 * 
 * @author flrnb
 */
public class DAOMockBuilder<E, T extends GenericDAO<E, Long>> {

	private final T mock;

	private final AtomicLong ids = new AtomicLong(0L);

	private final List<E> entities = new ArrayList<E>();

	public static <E, T extends GenericDAO<E, Long>> DAOMockBuilder<E, T> forClass(Class<T> clazz) {
		return new DAOMockBuilder<E, T>(clazz);
	}

	private DAOMockBuilder(Class<T> toMock) {
		this.mock = EasyMock.createNiceMock(toMock);
	}

	public DAOMockBuilder<E, T> addEntities(List<E> entities) {
		for (final E e : entities) {
			setId(e);
			this.entities.add(e);
		}
		return this;
	}

	public DAOMockBuilder<E, T> addEntities(E... entities) {
		return this.addEntities(Arrays.asList(entities));
	}

	@SuppressWarnings("unchecked")
	public T build() {
		EasyMock.expect(mock.findAll()).andReturn(entities).anyTimes();
		EasyMock.expect(mock.findById(EasyMock.anyLong(), EasyMock.anyBoolean())).andAnswer(new IAnswer<E>() {
			@Override
			public E answer() throws Throwable {
				Long id = (Long) EasyMock.getCurrentArguments()[0];
				for (E e : entities)
					if (id.equals(BeanUtils.getProperty(e, "id")))
						return e;
				return null;
			}
		});
		EasyMock.expect(mock.makePersistent((E) EasyMock.anyObject()));
		EasyMock.expectLastCall().andAnswer(new IAnswer<Void>() {
			@Override
			public Void answer() throws Throwable {
				E entity = (E) EasyMock.getCurrentArguments()[0];
				if (!entities.contains(entity)) {
					setId(entity);
					entities.add(entity);
				}
				return null;
			}
		}).anyTimes();
		EasyMock.replay(mock);
		return mock;
	}

	public static void main(String[] args) {
		Pruefungsleistung leistung = new Pruefungsleistung();
		leistung.setPruefungsDatum(new java.util.Date());
		PruefungsleistungDAO dao = DAOMockBuilder.forClass(PruefungsleistungDAO.class).addEntities(leistung).build();
		Long id = leistung.getId();
		Pruefungsleistung leistung2 = new Pruefungsleistung();
		dao.makePersistent(leistung2);
		dao.makePersistent(leistung);
		Long id2 = leistung2.getId();
		List<Pruefungsleistung> findAll = dao.findAll();
		System.out.println(id.toString() + id2 + findAll);
	}

	private void setId(final E e) {
		Long id = ids.incrementAndGet();
		try {
			BeanUtils.setProperty(e, "id", id);
		} catch (Exception exc) {
			throw new IllegalArgumentException("Klasse " + e.getClass().getSimpleName() + " hat kein ID-Feld");
		}
	}

}
