package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;

import java.util.Optional;

@Repository
public class JpaRestaurantRepository extends JpaManagedEntityRepository<Restaurant> implements RestaurantRepository  {

    private final IJpaRestaurantRepository repository;

    public JpaRestaurantRepository(IJpaRestaurantRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return repository.findByNameAndAddress(name, address);
    }
}
