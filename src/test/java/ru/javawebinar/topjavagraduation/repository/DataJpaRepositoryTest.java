package ru.javawebinar.topjavagraduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.*;
import ru.javawebinar.topjavagraduation.data.TestData;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/test.xml",
})
public class DataJpaRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void checkRestaurantSave() {
        restaurantRepository.clean();
        restaurantRepository.save(new Restaurant("cafe1", "address1"));
        Restaurant restaurant = restaurantRepository.findByName("cafe1").get(0);
        assertNotNull(restaurant);
        assertEquals("cafe1", restaurant.getName());
    }

    @Test
    @Transactional
    void checkMealSave() {
        mealRepository.clean();
        restaurantRepository.clean();
        restaurantRepository.getAll(); // cache clean-up
        Restaurant restaurant = restaurantRepository.save(new Restaurant("cafe1", "address1"));
        mealRepository.save(new Meal("meal1", 250.0f, restaurant));
        Meal meal = mealRepository.findByName("meal1").get(0);
        assertEquals("meal1", meal.getName());
    }

    @Test
    @Transactional
    void checkMenuSave() {
        menuRepository.clean();
        mealRepository.clean();
        restaurantRepository.clean();
        restaurantRepository.getAll(); // cache clean-up
        Restaurant restaurant = restaurantRepository.save(new Restaurant("cafe1", "address1"));
        Meal meal11 = mealRepository.save(new Meal("meal11", 250.0f, restaurant));
        Meal meal12 = mealRepository.save(new Meal("meal12", 250.0f, restaurant));
        Set<Meal> meals = Set.of(meal11, meal12);
        menuRepository.save(new Menu(null, TestData.date, restaurant, meals));
        Menu menu = menuRepository.getAll().get(0);
        assertNotNull(menu);
        assertEquals(restaurant, menu.getRestaurant());
    }

    @Test
    void checkUserSave() {
        userRepository.clean();
        userRepository.save(new User("newUser", "newuser@restaurants.ru", Role.USER, "12345"));
        User user = userRepository.findByEmail("newuser@restaurants.ru").orElse(null);
        assertNotNull(user);
        assertEquals("newuser@restaurants.ru", user.getEmail());
    }

    @Test
    @Transactional
    void checkVoteSave() {
        voteRepository.clean();
        userRepository.clean();
        restaurantRepository.clean();
        restaurantRepository.getAll(); // cache clean-up
        Restaurant restaurant = restaurantRepository.save(new Restaurant("cafe1", "address1"));
        User user = userRepository.save(new User("newUser", "newuser@restaurants.ru", Role.USER, "12345"));
        Vote vote = voteRepository.save(new Vote(user, restaurant));
        assertNotNull(vote);
        assertEquals(restaurant, vote.getRestaurant());
        assertEquals(user, vote.getUser());
    }
}
