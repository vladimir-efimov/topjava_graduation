package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface IJpaMenuRepository extends IJpaBaseEntityRepository<Menu> {

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.meals WHERE m.restaurant.id=:id")
    List<Menu> findByRestaurant(@Param("id") int id);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.meals LEFT JOIN FETCH m.restaurant WHERE m.date=:date")
    List<Menu> findByDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.meals WHERE m.restaurant.id=:id AND m.date=:date")
    Optional<Menu> findByRestaurantAndDate(@Param("id") int id, @Param("date") LocalDate date);
}
