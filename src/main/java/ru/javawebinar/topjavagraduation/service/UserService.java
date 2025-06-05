package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.JpaUserRepository;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;

import java.util.Optional;
import java.util.Set;


@Service
public class UserService extends AbstractManagedEntityService<User> {

    private final JpaUserRepository repository;
    private static final Set<String> systemUserEmails = Set.of("admin@restaurants.ru", "user@restaurants.ru");
    /**
     * User can request to delete its data, but service has rights to keep anonimized voting data
     * (and use it for analytical reports for example)
     * Let's for simplicity count that restaurants.ru belongs to service and mails like xxx_erased@restaurants.ru are
     * not possible
     */
    private static final User erasedUser = new User(null, false, "_erased", "_erased@restaurants.ru", Set.of(), "_erased");

    public UserService(JpaUserRepository repository) {
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
        } catch (CloneNotSupportedException ex) {
        }
    }

    boolean isSystemUser(User user) {
        return systemUserEmails.contains(user.getEmail());
    }

    @Override
    protected void validateOperation(User user, CrudOperation operation) {
        super.validateOperation(user, operation);
        if (operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
            User savedUser = get(user.getId());
            if (isSystemUser(savedUser)) {
                throw new IllegalOperationException("Can't " + operation + " system user");
            }
        }
    }
}
