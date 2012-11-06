package de.nak.iaa;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Oberklasse für Testklassen, die den ApplicationContext brauchen
 * 
 * @author Florian Borchert
 * @version 06.11.2012
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public abstract class ApplicationContextAwareTest {

	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	public ApplicationContext applicationContext;

	@Autowired
	public PlatformTransactionManager platformTransactionManager;

	protected TransactionTemplate transactionTemplate;

	private static LocalSessionFactoryBean sessionFactoryBean;

	/**
	 * vor jedem Test einmal das komplette Schema der Datenbank löschen
	 */
	@Before
	public void beforeMethod() {
		if (sessionFactoryBean == null)
			sessionFactoryBean = (LocalSessionFactoryBean) applicationContext.getBean("&sessionFactory");
		sessionFactoryBean.dropDatabaseSchema();
		sessionFactoryBean.createDatabaseSchema();
		transactionTemplate = new TransactionTemplate(platformTransactionManager);
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				doSetUp();
				return null;
			}
		});
	}

	protected abstract void doSetUp();

	@After
	public void afterMethod() {
	}

	@AfterClass
	public static void tearDownAfterClass() {
		if (sessionFactoryBean != null) {
			sessionFactoryBean.dropDatabaseSchema();
			sessionFactoryBean.createDatabaseSchema();
		}
	}

}
