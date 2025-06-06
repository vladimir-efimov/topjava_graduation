package ru.javawebinar.topjavagraduation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.JpaRestaurantRepository;


@Service
public class RestaurantService extends AbstractManagedEntityService<Restaurant> {

    private final JpaRestaurantRepository repository;
    public RestaurantService(JpaRestaurantRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getEnabled() {
        return super.getEnabled();
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        return super.create(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        super.update(restaurant);
    }

    public List<Restaurant> findByAddress(String address) {
        return repository.findByAddress(address);
    }

    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return repository.findByNameAndAddress(name, address);
    }
}
