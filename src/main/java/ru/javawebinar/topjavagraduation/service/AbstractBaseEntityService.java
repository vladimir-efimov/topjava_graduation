package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.BaseEntityRepository;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

import java.util.List;

public abstract class AbstractBaseEntityService<T extends AbstractBaseEntity> {
    private final BaseEntityRepository<T> repository;

    protected AbstractBaseEntityService(BaseEntityRepository<T> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        validateOnCreate(entity);
        T savedEntity = repository.save(entity);
        if (savedEntity == null) {
            //todo: substitute exception
            throw new IllegalStateException("Failed to create " + entity.getClass().getSimpleName());
        }
        return savedEntity;
    }

    public void update(T entity) {
        validateOnUpdate(entity);
        if (repository.save(entity) == null) {
            throw new IllegalStateException("Failed to update " + entity.getClass().getSimpleName());
        }
    }

    public void delete(int id) {
        T entity = get(id);
        validateOnDelete(entity);
        if (!repository.delete(id)) {
            throw new IllegalStateException("Can't delete " + entity.getClass().getSimpleName() + " with id " + id);
        }
    }

    public T get(int id) {
        T entity = repository.get(id);
        if (entity == null) {
            throw new NotFoundException("User with id " + id + "is not found");
        }
        return entity;
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    protected void validateOnCreate(T entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Input can't be null");
        }
        if(entity.getId() != null) {
            throw new IllegalStateException("Id should be null for create operation");
        }
    }

    protected void validateOnUpdate(T entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Input data can't be null");
        }
        if(entity.getId() == null) {
            throw new IllegalStateException("Id should not be null for update operation");
        }
    }

    protected void validateOnDelete(T entity) {
    }
}
