package ru.javawebinar.topjavagraduation.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class InMemoryVoteRepository extends InMemoryBaseEntityRepository<Vote> implements VoteRepository {
    public List<Vote> findByUser(int id) {
        return entities.values().stream()
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(id))
                .toList();
    }

    public List<Vote> findByDate(LocalDate date) {
        return entities.values().stream()
                .filter(e -> e.getDate()!= null && e.getDate().equals(date))
                .toList();
    }
}
