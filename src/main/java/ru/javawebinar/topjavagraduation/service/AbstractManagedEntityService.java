package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.repository.JpaManagedEntityRepository;

import java.util.List;

public class AbstractManagedEntityService<T extends AbstractManagedEntity> extends AbstractBaseEntityService<T> {

    private final JpaManagedEntityRepository<T> repository;

    protected AbstractManagedEntityService(JpaManagedEntityRepository<T> repository) {
        super(repository);
        this.repository = repository;
    }

    public List<T> getEnabled() {
        return repository.getEnabled();
    }

    public List<T> findByName(String name) {
        return repository.findByName(name);
    }

}
