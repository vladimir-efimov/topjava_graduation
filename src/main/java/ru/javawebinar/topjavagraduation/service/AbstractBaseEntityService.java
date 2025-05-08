package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.JpaBaseEntityRepository;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

import java.util.List;


public abstract class AbstractBaseEntityService<T extends AbstractBaseEntity> {
    private final JpaBaseEntityRepository<T> repository;

    protected AbstractBaseEntityService(JpaBaseEntityRepository<T> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        validateOperation(entity, CrudOperation.CREATE);
        return repository.save(entity);
    }

    public void update(T entity) {
        validateOperation(entity, CrudOperation.UPDATE);
        repository.save(entity);
    }

    public T get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " is not found"));
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    protected void validateOperation(T entity, CrudOperation operation) {
        if (entity.getId() == null) {
            if (operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
                throw new IllegalArgumentException("Id should not be null for " + operation + " operation");
            }
        } else {
            if (operation == CrudOperation.CREATE) {
                throw new IllegalArgumentException("Id should be null for create operation");
            }
        }
        entity.assertValid();
    }
}
