package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;

import java.util.Collection;

public interface ManagedEntityRepository<T extends AbstractManagedEntity> {
    // returns null if entity is not saved or entity with id
    T save(T entity);

    boolean delete(int id);

    T get(int id);

    Collection<T> getAll();

    Collection<T> getEnabled();

    Collection<T> findByName(String name);
}
