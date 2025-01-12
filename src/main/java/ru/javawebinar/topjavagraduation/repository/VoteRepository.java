package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Vote;

import java.util.List;

public interface VoteRepository extends BaseEntityRepository<Vote> {
    List<Vote> findByUser(int id);
}
