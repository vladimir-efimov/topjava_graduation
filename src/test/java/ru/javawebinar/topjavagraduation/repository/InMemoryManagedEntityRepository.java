package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryManagedEntityRepository<T extends AbstractManagedEntity> implements ManagedEntityRepository<T> {

    static final AtomicInteger idValue = new AtomicInteger(1);
    final Map<Integer, T> entities = new ConcurrentHashMap<>();

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        if (entity.isNew()) {
            entity.setId(idValue.incrementAndGet());
            entities.put(entity.getId(), entity);
            return entity;
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
    public Collection<T> getAll() {
        return entities.values();
    }

    @Override
    public Collection<T> getEnabled() {
        return entities.values().stream()
                .filter(AbstractManagedEntity::isEnabled)
                .toList();
    }

    @Override
    public Collection<T> findByName(String name) {
        return entities.values().stream()
                .filter(e -> e.getName().equals(name))
                .toList();
    }
}
