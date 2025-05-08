package ru.javawebinar.topjavagraduation.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;

import java.util.List;

@NoRepositoryBean
public interface JpaManagedEntityRepository<T extends AbstractManagedEntity> extends JpaBaseEntityRepository<T> {

    List<T> findByName(@Param("name") String name);
    List<T> findByEnabled(@Param("enabled") boolean enabled);

    default List<T> getEnabled() {
        return findByEnabled(true);
    }
}
