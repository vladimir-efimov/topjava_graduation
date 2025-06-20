package ru.javawebinar.topjavagraduation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.JpaRestaurantRepository;


@Service
public class RestaurantService extends AbstractNamedEntityService<Restaurant> {

    private final JpaRestaurantRepository repository;
    public RestaurantService(JpaRestaurantRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    public List<Restaurant> findByAddress(String address) {
        return repository.findByAddress(address);
    }

    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return repository.findByNameAndAddress(name, address);
    }

    @Override
    protected void evictCache(Restaurant restaurant) {
        cacheManager.getCache("restaurants").clear();
        log.debug("Evict cache for restaurants");
    }
}
