package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.BaseEntityRepository;

import java.util.List;

public abstract class JpaBaseEntityRepository<T extends AbstractBaseEntity>
        implements BaseEntityRepository<T> {

    protected final JpaRepository<T, Integer> repository;

    protected JpaBaseEntityRepository(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    // returns null if entity is not saved or entity with id
    public T save(T entity) {
        return repository.save(entity);
    }

    public boolean delete(int id) {
        T entity = get(id);
        if (entity == null) return false;
        repository.delete(entity);
        return true;
    }

    public T get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public void clean() {
        repository.deleteAll();
    }
}
