package ru.javawebinar.topjavagraduation.repository.inmemory;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.BaseEntityRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;


public class InMemoryBaseEntityRepository<T extends AbstractBaseEntity> implements BaseEntityRepository<T> {

    protected final Map<Integer, T> entities = new HashMap<>();

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        if (entity.isNew()) {
            T newEntity;
            try {
                newEntity = (T) entity.clone();
            } catch (CloneNotSupportedException ex) {
                newEntity = entity;
            }
            newEntity.setId(entities.size() + 1);
            entities.put(newEntity.getId(), newEntity);
            return newEntity;
        }
        return entities.computeIfPresent(entity.getId(), (id, oldT) -> entity);
    }

    @Override
    public boolean delete(int id) {
        return entities.remove(id) != null;
    }

    @Override
    public T get(int id) {
        return entities.get(id);
    }

    @Override
    public List<T> getAll() {
        return entities.values().stream().toList();
    }

    @Override
    public void clean() {
        entities.clear();
    }
}
