package ru.javawebinar.topjavagraduation.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class InMemoryVoteRepository extends InMemoryBaseEntityRepository<Vote> implements VoteRepository {

    @Override
    public List<Vote> findByUser(int id) {
        return entities.values().stream()
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(id))
                .toList();
    }

    @Override
    public List<Vote> findByDate(LocalDate date) {
        return entities.values().stream()
                .filter(e -> e.getDate()!= null && e.getDate().equals(date))
                .toList();
    }

    @Override
    public Optional<Vote> findByUserAndDate(int id, LocalDate date) {
        return entities.values().stream()
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(id) && e.getDate().equals(date))
                .findFirst();
    }
}
