package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Meal;

import java.util.List;

public interface MealRepository extends ManagedEntityRepository<Meal> {

    List<Meal> findByRestaurant(int id);
}
