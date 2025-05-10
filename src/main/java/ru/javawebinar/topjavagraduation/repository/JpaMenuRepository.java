package ru.javawebinar.topjavagraduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface JpaMenuRepository extends JpaBaseEntityRepository<Menu> {

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes LEFT JOIN FETCH m.restaurant WHERE m.id=:id")
    Optional<Menu> getWithDishesAndRestaurant(@Param("id") Integer id);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes WHERE m.restaurant.id=:id")
    List<Menu> getFilteredByRestaurantWithDishes(@Param("id") int id);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes LEFT JOIN FETCH m.restaurant WHERE m.date=:date")
    List<Menu> getFilteredByDateWithDishesAndRestaurant(@Param("date") LocalDate date);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes WHERE m.restaurant.id=:id AND m.date=:date")
    Optional<Menu> getFilteredByRestaurantAndDateWithDishes(@Param("id") int id, @Param("date") LocalDate date);
}
