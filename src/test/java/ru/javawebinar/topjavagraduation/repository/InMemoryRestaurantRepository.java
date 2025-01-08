package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Restaurant;

import java.util.Optional;

public class InMemoryRestaurantRepository extends InMemoryManagedEntityRepository<Restaurant> implements RestaurantRepository {

    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return entities.values().stream()
                .filter(e -> e.getName().equals(name) && e.getAddress().equals(address))
                .findFirst();
    }
}
