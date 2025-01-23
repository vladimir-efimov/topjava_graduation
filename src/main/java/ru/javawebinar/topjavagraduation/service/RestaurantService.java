package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;

@Service
public class RestaurantService extends AbstractManagedEntityService<Restaurant>{

    public RestaurantService(RestaurantRepository repository) {
        super(repository);
    }
}
