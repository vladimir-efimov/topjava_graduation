package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.User;

import java.util.Optional;

public class InMemoryUserRepository extends InMemoryManagedEntityRepository<User> implements UserRepository {
    public Optional<User> findByEmail(String email) {
        return entities.values().stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst();
    }
}
