package ru.javawebinar.topjavagraduation.repository.datajpa;

import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.repository.ManagedEntityRepository;

import java.util.List;

public abstract class JpaManagedEntityRepository<T extends AbstractManagedEntity> extends JpaBaseEntityRepository<T>
        implements ManagedEntityRepository<T> {

    protected final IJpaManagedEntityRepository<T> repository;

    protected JpaManagedEntityRepository(IJpaManagedEntityRepository<T> repository) {
        super(repository);
        this.repository = repository;
    }

    public List<T> getEnabled() {
        return repository.findByEnabled(true);
    }

    public List<T> findByName(String name) {
        return repository.findByName(name);
    }
}
