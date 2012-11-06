package de.nak.iaa;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Oberklasse für Testklassen, die den ApplicationContext brauchen
 * 
 * @author flrnb
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public abstract class ApplicationContextAwareTest {

	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	public ApplicationContext applicationContext;

	/**
	 * vor jedem Test einmal das komplette Schema der Datenbank löschen
	 */
	@Before
	public void beforeMethod() {
		LocalSessionFactoryBean sessionFactoryBean = (LocalSessionFactoryBean) applicationContext
				.getBean("&sessionFactory");
		sessionFactoryBean.dropDatabaseSchema();
		sessionFactoryBean.createDatabaseSchema();
	}

	@After
	public void afterMethod() {
		sessionFactory.close();
	}

}
