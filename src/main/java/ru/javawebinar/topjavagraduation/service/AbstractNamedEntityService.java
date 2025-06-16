package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.AbstractNamedEntity;
import ru.javawebinar.topjavagraduation.repository.JpaNamedEntityRepository;

import java.util.List;

public class AbstractNamedEntityService<T extends AbstractNamedEntity> extends AbstractBaseEntityService<T> {

    private final JpaNamedEntityRepository<T> repository;

    protected AbstractNamedEntityService(JpaNamedEntityRepository<T> repository) {
        super(repository);
        this.repository = repository;
    }

    public List<T> findByName(String name) {
        return repository.findByName(name);
    }

}
