package ru.javawebinar.topjavagraduation.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;

import java.util.Optional;

@Repository
public class InMemoryRestaurantRepository extends InMemoryManagedEntityRepository<Restaurant> implements RestaurantRepository {

    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return entities.values().stream()
                .filter(e -> e.getName().equals(name) && e.getAddress().equals(address))
                .findFirst();
    }
}
