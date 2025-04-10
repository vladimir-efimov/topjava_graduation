package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjavagraduation.model.User;

import java.util.Optional;

public interface IJpaUserRepository extends IJpaBaseEntityRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
}
