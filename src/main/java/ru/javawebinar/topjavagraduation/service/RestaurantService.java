package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;

public class RestaurantService extends AbstractManagedEntityService<Restaurant>{
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
