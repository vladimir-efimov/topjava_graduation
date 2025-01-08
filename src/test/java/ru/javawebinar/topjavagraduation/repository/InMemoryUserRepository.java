package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.User;

public class InMemoryUserRepository extends InMemoryManagedEntityRepository<User> implements UserRepository {
}
