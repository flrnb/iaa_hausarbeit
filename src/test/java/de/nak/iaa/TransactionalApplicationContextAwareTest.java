package de.nak.iaa;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Each method is run within a transaction which is rolled back when method is
 * over. Hibernate sessions which used in tests are have
 * {@link org.hibernate.FlushMode#ALWAYS} <br/>
 * 
 * <b>angepasst von Archetyp http://code.google.com/p/spring-archetypes/</b>
 * 
 * @author flrnb
 * 
 * @see org.springframework.test.context.transaction.TransactionConfiguration
 * @see org.springframework.transaction.annotation.Transactional
 * @see org.hibernate.Session#setFlushMode(org.hibernate.FlushMode)
 */
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public abstract class TransactionalApplicationContextAwareTest extends ApplicationContextAwareTest {

	@Autowired
	public SessionFactory sessionFactory;

	@Override
	protected void doSetUp() {
	}

	@Override
	@Before
	public void beforeMethod() {
		sessionFactory.getCurrentSession().setFlushMode(FlushMode.ALWAYS);
	}

	@Override
	@After
	public void afterMethod() {

	}

}
