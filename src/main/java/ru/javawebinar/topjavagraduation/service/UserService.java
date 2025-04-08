package ru.javawebinar.topjavagraduation.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.UserRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService extends AbstractManagedEntityService<User> {

    private final UserRepository repository;
    private static final User[] systemUsers = {
        new User("Admin", "admin@restaurants.ru", Role.ADMIN, "admin"),
        new User("SimpleUser", "user@restaurants.ru", Role.USER, "user1")
    };
    private static final User erasedUser = new User(null, false, "_erased", "_erased", Set.of(), "_erased");
    private final Set<Integer> systemUserIds = new HashSet<>();

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void erase(int userId) {
        try {
            User user = (User) erasedUser.clone();
            user.setId(userId);
            user.setEmail(user.getId() + user.getEmail());
            repository.save(user);
        } catch (CloneNotSupportedException ex) {}
    }

    static User[] getSystemUsers() {
        return systemUsers;
    }

    boolean isSystemUser(User user) {
        return systemUserIds.contains(user.getId());
    }

    @Override
    protected void validateOperation(User user, CrudOperation operation) {
        super.validateOperation(user, operation);
        if(operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
            if(isSystemUser(user)) {
                throw new IllegalOperationException("Can't " + operation + " system user");
            }
        }
    }

    @PostConstruct
    void init() {
        systemUserIds.clear();
        for(User user: systemUsers) {
            Optional<User> result = findByEmail(user.getEmail());
            Integer id = result.isEmpty() ?
                    repository.save(new User(user.getName(), user.getEmail(), user.getRoles(), user.getPassword())).getId()
                    : result.get().getId();
            systemUserIds.add(id);
        }
    }
}
