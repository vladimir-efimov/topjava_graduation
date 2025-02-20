package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.domain.Specification;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.repository.ManagedEntityRepository;

import java.util.List;

public abstract class JpaManagedEntityRepository<T extends AbstractManagedEntity> extends JpaBaseEntityRepository<T>
        implements ManagedEntityRepository<T> {

    protected final IJpaBaseEntityRepository<T> repository;

    // It seems HQL is not applicable for template classes, so decided to use specifications
    private final Specification<T> getEnabledSpecification =
        (root, query, builder) -> builder.equal(root.get("enabled"), true);

    private Specification<T> hasName(String name) {
        return (root, query, builder) -> builder.equal(root.get("name"), name);
    }

    protected JpaManagedEntityRepository(IJpaBaseEntityRepository<T> repository) {
        super(repository);
        this.repository = repository;
    }

    public List<T> getEnabled() {
        return repository.findAll(getEnabledSpecification);
    }

    public List<T> findByName(String name) {
        return repository.findAll(hasName(name));
    }
}
