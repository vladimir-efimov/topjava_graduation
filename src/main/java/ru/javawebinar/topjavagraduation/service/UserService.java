package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserService extends AbstractManagedEntityService<User> {

    private final UserRepository repository;
    private static final User[] systemUsers = {
        new User("Admin", "admin@restaurants.ru", Role.ADMIN, ""),
        new User("SimpleUser", "user@restaurants.ru", Role.USER, "")
    };
    private Set<Integer> systemUserIds = new HashSet<>();

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
        for(User user: systemUsers) {
            Optional<User> result = findByEmail(user.getEmail());
            Integer id = result.isEmpty() ? repository.save(user).getId() : result.get().getId();
            systemUserIds.add(id);
        }
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    static User[] getSystemUsers() {
        return systemUsers;
    }

    boolean isSystemUser(User user) {
        return systemUserIds.contains(user.getId());
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
