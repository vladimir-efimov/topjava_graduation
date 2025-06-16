package ru.javawebinar.topjavagraduation.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjavagraduation.model.AbstractNamedEntity;

import java.util.List;

@NoRepositoryBean
public interface JpaNamedEntityRepository<T extends AbstractNamedEntity> extends JpaBaseEntityRepository<T> {
    List<T> findByName(@Param("name") String name);
}
