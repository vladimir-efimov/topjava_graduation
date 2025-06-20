package ru.javawebinar.topjavagraduation.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.JpaUserRepository;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;

import java.util.Optional;
import java.util.Set;


@Service
public class UserService extends AbstractNamedEntityService<User> {

    private final JpaUserRepository repository;
    private static final Set<String> systemUserEmails = Set.of("admin@restaurants.ru", "user@restaurants.ru");

    public UserService(JpaUserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Cacheable(value = "users")
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
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

    @Override
    protected void evictCache(User user) {
        cacheManager.getCache("users").evictIfPresent(user.getEmail());
        log.debug("Evict cache for user with e-mail " + user.getEmail());
    }
}
