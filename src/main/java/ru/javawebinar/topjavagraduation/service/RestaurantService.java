package ru.javawebinar.topjavagraduation.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;


@Service
public class RestaurantService extends AbstractManagedEntityService<Restaurant> {

    private final RestaurantRepository repository;
    public RestaurantService(RestaurantRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Restaurant> findByAddress(String address) {
        return repository.findByAddress(address);
    }

    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return repository.findByNameAndAddress(name, address);
    }
}
