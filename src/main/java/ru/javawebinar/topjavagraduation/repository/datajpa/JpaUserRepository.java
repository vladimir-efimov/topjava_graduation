package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.UserRepository;

import java.util.Optional;

@Repository
public class JpaUserRepository extends JpaManagedEntityRepository<User> implements UserRepository {

    private final IJpaUserRepository repository;

    public JpaUserRepository(IJpaUserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
