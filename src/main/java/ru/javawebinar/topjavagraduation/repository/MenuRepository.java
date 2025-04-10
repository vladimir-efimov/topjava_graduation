package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends BaseEntityRepository<Menu> {
    List<Menu> findByRestaurant(int id);

    List<Menu> findByDate(LocalDate date);

    Optional<Menu> findByRestaurantAndDate(int id, LocalDate date);
}
