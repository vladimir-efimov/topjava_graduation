package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseEntityRepository<Vote> {
    List<Vote> findByUser(int id);
    List<Vote> findByDate(LocalDate date);
    Optional<Vote> findByUserAndDate(int id, LocalDate date);
}
