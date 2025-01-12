package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;

import java.util.List;

public interface BaseEntityRepository <T extends AbstractBaseEntity> {

    // returns null if entity is not saved or entity with id
    T save(T entity);

    boolean delete(int id);

    T get(int id);

    List<T> getAll();
}
