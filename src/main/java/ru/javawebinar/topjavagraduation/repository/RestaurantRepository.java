package ru.javawebinar.topjavagraduation.repository;

import java.util.Optional;

import ru.javawebinar.topjavagraduation.model.Restaurant;

public interface RestaurantRepository extends ManagedEntityRepository<Restaurant> {
    Optional<Restaurant> findByNameAndAddress(String name, String address);
}
