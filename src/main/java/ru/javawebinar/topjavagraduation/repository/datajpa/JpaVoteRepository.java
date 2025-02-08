package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JpaVoteRepository  extends JpaBaseEntityRepository<Vote> implements VoteRepository {

    private final IJpaVoteRepository repository;

    public JpaVoteRepository(IJpaVoteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Vote> findByUser(int id) {
        return repository.findByUser(id);
    }

    public List<Vote> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }
}
