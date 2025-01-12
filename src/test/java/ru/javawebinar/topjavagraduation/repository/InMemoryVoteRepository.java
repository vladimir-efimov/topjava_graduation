package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Vote;

import java.util.List;

public class InMemoryVoteRepository extends InMemoryBaseEntityRepository<Vote> implements VoteRepository {
    public List<Vote> findByUser(int id) {
        return entities.values().stream()
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(id))
                .toList();
    }
}
