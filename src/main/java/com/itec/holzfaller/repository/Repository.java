package com.itec.holzfaller.repository;

import com.itec.holzfaller.common.DatabaseWrapper;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Repository<T, R extends Serializable> {

    private DatabaseWrapper wrapper = new DatabaseWrapper();
    private Class<T> clasz;

    public Repository(Class<T> clasz) {
        this.clasz = clasz;
    }

    public List<T> findAll() {
        return wrapper.executeWithReturn(entityManager -> entityManager.createQuery("select p from " + clasz.getCanonicalName() + " p", this.clasz)
                                                                       .getResultList());
    }

    public void save(T entity) {
        wrapper.executeWithoutReturn(entityManager -> entityManager.persist(entity));
    }

    //TODO this can be problematic
    public void delete(T entity) {
        wrapper.executeWithoutReturn(entityManager -> entityManager.remove(entity));
    }

    public void delete(R primaryKey) {
        wrapper.executeWithoutReturn(entityManager -> {
            T entity = entityManager.find(clasz, primaryKey);
            entityManager.remove(entity);
        });
    }

    public T update(T entity) {
        return wrapper.executeWithReturn(entityManager -> entityManager.merge(entity));
    }

    public T findOne(R primaryKey) {
        return wrapper.executeWithReturn(entityManager -> entityManager.find(clasz, primaryKey));
    }

    protected void executeWithoutReturn(Consumer<EntityManager> action) {
        wrapper.executeWithoutReturn(action);
    }

    protected <U> U executeWithReturn(Function<EntityManager, U> action) {
        return wrapper.executeWithReturn(action);
    }

}
