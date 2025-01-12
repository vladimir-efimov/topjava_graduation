package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBaseEntityRepository <T extends AbstractBaseEntity> implements BaseEntityRepository<T> {

    protected final AtomicInteger idValue = new AtomicInteger(0);
    protected final Map<Integer, T> entities = new ConcurrentHashMap<>();

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        if (entity.isNew()) {
            T newEntity;
            try {
                newEntity = (T) entity.clone();
            } catch(CloneNotSupportedException ex) {
                newEntity = entity;
            }
            newEntity.setId(idValue.incrementAndGet());
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
}
