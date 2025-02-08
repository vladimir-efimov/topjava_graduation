package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;

@NoRepositoryBean
public interface IJpaBaseEntityRepository<T extends AbstractBaseEntity>
        extends JpaRepository <T, Integer>, JpaSpecificationExecutor<T> {
}
