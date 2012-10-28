package de.nak.iaa.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.beanutils.BeanUtils;
import org.easymock.EasyMock;
import org.easymock.IAnswer;

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

	protected DAOMockBuilder(Class<T> toMock) {
		this.mock = EasyMock.createNiceMock(toMock);
	}

	public DAOMockBuilder<E, T> addEntities(List<E> entities) {
		for (final E e : entities) {
			setId(e);
			this.getEntities().add(e);
		}
		return this;
	}

	public DAOMockBuilder<E, T> addEntities(E... entities) {
		return this.addEntities(Arrays.asList(entities));
	}

	@SuppressWarnings("unchecked")
	public T build() {
		EasyMock.expect(getMock().findAll()).andReturn(getEntities()).anyTimes();
		EasyMock.expect(getMock().findById(EasyMock.anyLong(), EasyMock.anyBoolean())).andAnswer(new IAnswer<E>() {
			@Override
			public E answer() throws Exception {
				Long id = (Long) EasyMock.getCurrentArguments()[0];
				for (E e : getEntities())
					if (id.equals(Long.valueOf(BeanUtils.getProperty(e, "id"))))
						return e;
				return null;
			}
		}).anyTimes();
		EasyMock.expect(getMock().makePersistent((E) EasyMock.anyObject())).andAnswer(new IAnswer<E>() {
			@Override
			public E answer() throws Throwable {
				E entity = (E) EasyMock.getCurrentArguments()[0];
				if (!getEntities().contains(entity)) {
					setId(entity);
					getEntities().add(entity);
				}
				return entity;
			}
		}).anyTimes();
		EasyMock.replay(getMock());
		return getMock();
	}

	private void setId(final E e) {
		Long id = ids.incrementAndGet();
		try {
			BeanUtils.setProperty(e, "id", id);
		} catch (Exception exc) {
			throw new IllegalArgumentException("Klasse " + e.getClass().getSimpleName() + " hat kein ID-Feld");
		}
	}

	protected T getMock() {
		return mock;
	}

	protected List<E> getEntities() {
		return entities;
	}

}
