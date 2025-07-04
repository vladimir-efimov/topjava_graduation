package ru.javawebinar.topjavagraduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjavagraduation.model.User;

import java.util.Optional;

public interface JpaUserRepository extends JpaNamedEntityRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
}
