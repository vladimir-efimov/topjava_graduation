package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.BaseEntityRepository;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.validation.exception.RepositoryOperationException;

import java.util.List;

public abstract class AbstractBaseEntityService<T extends AbstractBaseEntity> {
    private final BaseEntityRepository<T> repository;

    protected AbstractBaseEntityService(BaseEntityRepository<T> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        validateOperation(entity, CrudOperation.CREATE);
        T savedEntity = repository.save(entity);
        if (savedEntity == null) {
            throw new RepositoryOperationException("Failed to create " + entity.getClass().getSimpleName());
        }
        return savedEntity;
    }

    public void update(T entity) {
        validateOperation(entity, CrudOperation.UPDATE);
        if (repository.save(entity) == null) {
            throw new RepositoryOperationException("Failed to update " + entity.getClass().getSimpleName());
        }
    }

    public T get(int id) {
        T entity = repository.get(id);
        if (entity == null) {
            throw new NotFoundException("Entity with id " + id + " is not found");
        }
        return entity;
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    protected void validateOperation(T entity, CrudOperation operation) {
        if (entity.getId() == null) {
            if (operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
                throw new IllegalArgumentException("Id should not be null for " + operation +" operation");
            }
        } else {
            if (operation == CrudOperation.CREATE) {
                throw new IllegalArgumentException("Id should be null for create operation");
            }
        }
        entity.assertValid();
    }
}
