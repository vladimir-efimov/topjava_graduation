package ru.javawebinar.topjavagraduation.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.Restaurant;

import java.util.Optional;

public class InMemoryRestaurantRepositoryTest {

    InMemoryRestaurantRepository repository;

    @BeforeEach
    void setup() {
        repository = new InMemoryRestaurantRepository();
        repository.save(new Restaurant("cafe1", "address1"));
        repository.save(new Restaurant("cafe2", "address2_1"));
        repository.save(new Restaurant("cafe2", "address2_2"));
        repository.save(new Restaurant("cafe3", "address3"));
    }

    @Test
    void testRead() {
        Assertions.assertEquals(2, repository.findByName("cafe2").size());
        Optional<Restaurant> opt = repository.findByNameAndAddress("cafe2", "address1");
        Assertions.assertTrue(opt.isEmpty());
        opt = repository.findByNameAndAddress("cafe2", "address2_1");
        Assertions.assertTrue(opt.isPresent());
        Assertions.assertNotNull(opt.get().getId());
    }
}
