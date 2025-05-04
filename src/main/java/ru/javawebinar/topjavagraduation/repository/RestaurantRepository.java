package ru.javawebinar.topjavagraduation.repository;

import java.util.List;
import java.util.Optional;

import ru.javawebinar.topjavagraduation.model.Restaurant;

public interface RestaurantRepository extends ManagedEntityRepository<Restaurant> {

    List<Restaurant> findByAddress(String address);
    Optional<Restaurant> findByNameAndAddress(String name, String address);
}
