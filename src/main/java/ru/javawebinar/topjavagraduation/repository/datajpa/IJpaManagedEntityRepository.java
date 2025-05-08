package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;

import java.util.List;

@NoRepositoryBean
public interface IJpaManagedEntityRepository<T extends AbstractManagedEntity> extends IJpaBaseEntityRepository<T> {

    List<T> findByName(@Param("name") String name);
    List<T> findByEnabled(@Param("enabled") boolean enabled);
}
