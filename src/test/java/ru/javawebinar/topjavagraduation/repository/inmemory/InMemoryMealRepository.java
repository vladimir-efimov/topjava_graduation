package ru.javawebinar.topjavagraduation.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class InMemoryMealRepository extends InMemoryManagedEntityRepository<Meal> implements MealRepository {

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

    @Override
    public List<Meal> findByIds(List<Integer> ids) {
        List<Meal> meals = new ArrayList<>();
        ids.forEach(id -> meals.add(entities.get(id)));
        return meals;
    }
}
