package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Meal;

import java.util.List;

public interface MealRepository extends BaseEntityRepository<Meal> {

    List<Meal> findByName(String name);

    List<Meal> findByRestaurant(int id);
}
