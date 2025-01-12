package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.UserRepository;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository repository;
    private static final User[] systemUsers = {
        new User("Admin", "admin@restaurants.ru", Role.ADMIN, ""),
        new User("SimpleUser", "user@restaurants.ru", Role.USER, "")
    };

    public UserService(UserRepository repository) {
        this.repository = repository;
        for(User user: systemUsers) {
            if(findByEmail(user.getEmail()).isEmpty()) {
                repository.save(user);
            }
        }
    }

    public User create(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        if(user.getId() != null) {
            throw new IllegalStateException("Illegal operation for user with not null id");
        }
        User savedUser = repository.save(user);
        if ( savedUser == null) {
            throw new IllegalStateException("Failed to save user");
        }
        return savedUser;
    }

    public void update(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        if(user.getId() == null) {
            throw new IllegalStateException("User id should not be null");
        }
        if(isSystemUser(user)) {
            throw new IllegalStateException("Can't modify system user");
        }
        if (repository.save(user) == null) {
            throw new IllegalStateException("Failed to save user");
        }
    }

    public void delete(int id) {
        User user = get(id);
        if(isSystemUser(user)) {
            throw new IllegalStateException("Can't delete system user");
        }
        if (!repository.delete(id)) {
            throw new IllegalStateException("Can't delete user with id " + id);
        }
    }

    public User get(int id) {
        User user = repository.get(id);
        if (user == null) {
            throw new NotFoundException("User with id " + id + "is not found");
        }
        return user;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public List<User> getEnabled() {
        return repository.getEnabled();
    }

    public List<User> findByName(String name) {
        return repository.findByName(name);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    static User[] getSystemUsers() {
        return systemUsers;
    }

    static boolean isSystemUser(User user) {
        if(user == null) return false;
        return Arrays.stream(systemUsers)
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }
}
