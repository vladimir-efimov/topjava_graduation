package ru.javawebinar.topjavagraduation.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.User;

import java.util.Optional;

@Repository
public class InMemoryUserRepository extends InMemoryManagedEntityRepository<User> implements UserRepository {
    public Optional<User> findByEmail(String email) {
        return entities.values().stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst();
    }
}
