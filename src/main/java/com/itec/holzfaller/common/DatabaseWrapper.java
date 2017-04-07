package com.itec.holzfaller.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseWrapper {

    private EntityManager em;
    private EntityManagerFactory emFactory;

    public <R> R executeWithReturn(Function<EntityManager, R> action) {
        beginConnection();
        R result = action.apply(em);
        closeConnection();
        return result;
    }

    public void executeWithoutReturn(Consumer<EntityManager> action) {
        beginConnection();
        action.accept(em);
        closeConnection();
    }

    private void beginConnection() {
        em = getEntityManager();

        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void closeConnection() {
        if (em != null) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
    }

    private EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = createEntityManager();
        }
        return em;
    }

    private EntityManager createEntityManager() {
        if (emFactory == null || !emFactory.isOpen()) {
            emFactory = createEntityManagerFactory();
        }

        return emFactory.createEntityManager();
    }

    private EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
    }
}
