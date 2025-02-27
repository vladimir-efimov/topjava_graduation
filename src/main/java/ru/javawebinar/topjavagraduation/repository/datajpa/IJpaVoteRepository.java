package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface IJpaVoteRepository extends IJpaBaseEntityRepository<Vote> {

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.user.id=:id")
    List<Vote> findByUser(@Param("id") int id);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.date=:date")
    List<Vote> findByDate(@Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.user.id=:id AND v.date=:date")
    Optional<Vote> findByUserAndDate(@Param("id") int id, @Param("date") LocalDate date);
}
