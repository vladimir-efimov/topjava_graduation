package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;

import java.util.List;

public interface ManagedEntityRepository<T extends AbstractManagedEntity> {
    // returns null if entity is not saved or entity with id
    T save(T entity);

    boolean delete(int id);

    T get(int id);

    List<T> getAll();

    List<T> getEnabled();

    List<T> findByName(String name);
}
