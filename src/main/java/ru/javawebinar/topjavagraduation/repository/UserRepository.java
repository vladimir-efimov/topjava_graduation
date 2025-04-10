package ru.javawebinar.topjavagraduation.repository;

import java.util.Optional;

import ru.javawebinar.topjavagraduation.model.User;

public interface UserRepository extends ManagedEntityRepository<User> {
    Optional<User> findByEmail(String email);
}
