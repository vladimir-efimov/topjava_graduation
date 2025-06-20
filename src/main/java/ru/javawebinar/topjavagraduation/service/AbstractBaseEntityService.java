package ru.javawebinar.topjavagraduation.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.JpaBaseEntityRepository;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public abstract class AbstractBaseEntityService<T extends AbstractBaseEntity> {
    private final JpaBaseEntityRepository<T> repository;
    protected final Logger log = getLogger(getClass());
    @Autowired
    protected CacheManager cacheManager;

    protected AbstractBaseEntityService(JpaBaseEntityRepository<T> repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        validateOperation(entity, CrudOperation.CREATE);
        T savedEntity = repository.save(entity);
        evictCache(savedEntity);
        return savedEntity;
    }

    public void update(T entity) {
        validateOperation(entity, CrudOperation.UPDATE);
        repository.save(entity);
        evictCache(entity);
    }

    public T get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " is not found"));
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public void delete(int id) {
        T entity = get(id);
        validateOperation(entity, CrudOperation.DELETE);
        repository.delete(entity);
        evictCache(entity);
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
    }

    protected void evictCache(T entity) {}
}
