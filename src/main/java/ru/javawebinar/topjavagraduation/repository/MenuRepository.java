package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Menu;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends BaseEntityRepository<Menu> {
    List<Menu> findByRestaurant(int id);
    Optional<Menu> findByRestaurantAndDate(int id, Date date);
}
