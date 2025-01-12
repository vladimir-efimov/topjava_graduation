package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;

import java.util.List;

public interface ManagedEntityRepository<T extends AbstractManagedEntity> extends BaseEntityRepository<T> {

    List<T> getEnabled();

    List<T> findByName(String name);
}
