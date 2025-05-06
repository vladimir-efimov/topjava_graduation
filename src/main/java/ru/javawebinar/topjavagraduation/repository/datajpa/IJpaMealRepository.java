package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Meal;

import java.util.List;

@Transactional(readOnly = true)
public interface IJpaMealRepository extends IJpaBaseEntityRepository<Meal> {

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.restaurant WHERE m.id=:id")
    Meal getWithRestaurant(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:id")
    List<Meal> findByRestaurant(@Param("id") int id);
}
