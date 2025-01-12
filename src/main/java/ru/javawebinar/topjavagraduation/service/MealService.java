package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.repository.MealRepository;

import java.util.List;

public class MealService extends AbstractBaseEntityService<Meal> {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Meal> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Meal> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }
}
