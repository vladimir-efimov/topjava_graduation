package ru.javawebinar.topjavagraduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;

@NoRepositoryBean
public interface JpaBaseEntityRepository<T extends AbstractBaseEntity> extends JpaRepository<T, Integer> {
}
