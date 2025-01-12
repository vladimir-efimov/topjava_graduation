package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Meal;

import java.util.List;

public class InMemoryMealRepository extends InMemoryBaseEntityRepository<Meal> implements MealRepository {

    @Override
    public List<Meal> findByName(String name) {
        return entities.values().stream()
                .filter(e -> e.getName().equals(name))
                .toList();
    }

    @Override
    public List<Meal> findByRestaurant(int id) {
        return entities.values().stream()
                .filter(e -> e.getRestaurant() != null && e.getRestaurant().getId().equals(id))
                .toList();
    }
}
