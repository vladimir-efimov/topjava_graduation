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
        repository.save(new Restaurant("restaurant1", "address1"));
        repository.save(new Restaurant("fastfood2", "address2_1"));
        repository.save(new Restaurant("fastfood2", "address2_2"));
        repository.save(new Restaurant("cafe3", "address3"));
    }

    @Test
    void testRead() {
        Assertions.assertEquals(2, repository.findByName("fastfood2").size());
        Optional<Restaurant> opt = repository.findByNameAndAddress("fastfood2", "address1");
        Assertions.assertTrue(opt.isEmpty());
        opt = repository.findByNameAndAddress("fastfood2", "address2_1");
        Assertions.assertTrue(opt.isPresent());
        Assertions.assertNotNull(opt.get().getId());
    }

    @Test
    void testSave() {
        var restaurant = new Restaurant("restaurant4", "address4");
        var saved = repository.save(restaurant);
        Assertions.assertNull(restaurant.getId());
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(restaurant.getName(), saved.getName());
        Assertions.assertEquals(restaurant.getAddress(), saved.getAddress());
    }
}
