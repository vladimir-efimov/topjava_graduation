package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class JpaVoteRepository extends JpaBaseEntityRepository<Vote> implements VoteRepository {

    private final IJpaVoteRepository repository;

    public JpaVoteRepository(IJpaVoteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Vote> findByUser(int id) {
        return repository.findByUser(id);
    }

    @Override
    public List<Vote> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    @Override
    public Optional<Vote> findByUserAndDate(int id, LocalDate date) {
        return repository.findByUserAndDate(id, date);
    }
}
