package ru.javawebinar.topjavagraduation.repository.inmemory;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.repository.ManagedEntityRepository;

import java.util.List;


public class InMemoryManagedEntityRepository<T extends AbstractManagedEntity>
        extends InMemoryBaseEntityRepository<T> implements ManagedEntityRepository<T> {

    @Override
    public List<T> getEnabled() {
        return entities.values().stream()
                .filter(AbstractManagedEntity::isEnabled)
                .toList();
    }

    @Override
    public List<T> findByName(String name) {
        return entities.values().stream()
                .filter(e -> e.getName().equals(name))
                .toList();
    }
}
