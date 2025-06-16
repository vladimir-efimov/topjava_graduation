package ru.javawebinar.topjavagraduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface JpaDishRepository extends JpaNamedEntityRepository<Dish> {

    @Query("SELECT m FROM Dish m LEFT JOIN FETCH m.restaurant WHERE m.id=:id")
    Dish getWithRestaurant(@Param("id") int id);

    @Query("SELECT m FROM Dish m WHERE m.restaurant.id=:id")
    List<Dish> findByRestaurant(@Param("id") int id);
}
