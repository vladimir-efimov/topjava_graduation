package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends BaseEntityRepository<Vote> {
    List<Vote> findByUser(int id);
    List<Vote> findByDate(LocalDate date);
}
