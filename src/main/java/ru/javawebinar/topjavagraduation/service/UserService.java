package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

public class UserService extends AbstractManagedEntityService<User> {

    private final UserRepository repository;
    private static final User[] systemUsers = {
        new User("Admin", "admin@restaurants.ru", Role.ADMIN, ""),
        new User("SimpleUser", "user@restaurants.ru", Role.USER, "")
    };

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
        for(User user: systemUsers) {
            if(findByEmail(user.getEmail()).isEmpty()) {
                repository.save(user);
            }
        }
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

    @Override
    protected void validateOnUpdate(User user) {
        super.validateOnUpdate(user);
        if(isSystemUser(user)) {
            throw new IllegalStateException("Can't modify system user");
        }
    }

    @Override
    protected void validateOnDelete(User user) {
        super.validateOnDelete(user);
        if(isSystemUser(user)) {
            throw new IllegalStateException("Can't delete system user");
        }
    }
}
