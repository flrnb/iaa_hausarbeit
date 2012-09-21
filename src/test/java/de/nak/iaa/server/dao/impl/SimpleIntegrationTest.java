package de.nak.iaa.server.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;

import de.nak.iaa.ApplicationContextAwareTest;
import de.nak.iaa.server.dao.DummyDAO;
import de.nak.iaa.server.entity.DummyEntity;

import static org.junit.Assert.assertEquals;

/**
 * 
 */
public class SimpleIntegrationTest extends ApplicationContextAwareTest {

    @Resource
    private DummyDAO dummyDAO;

    /**
     * Tries to store {@link de.nak.iaa.server.entity.DummyEntity}.
     */
    @Test
    public void testPersistTestEntity() {
        int countBefore = dummyDAO.findAll().size();
        DummyEntity dummyEntity = new DummyEntity();
        dummyDAO.makePersistent(dummyEntity);

        int countAfter = dummyDAO.findAll().size();
        assertEquals(countBefore + 1, countAfter);
    }
}
