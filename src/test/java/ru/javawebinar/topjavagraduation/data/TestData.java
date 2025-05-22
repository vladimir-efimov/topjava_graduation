package ru.javawebinar.topjavagraduation.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.javawebinar.topjavagraduation.model.*;
import ru.javawebinar.topjavagraduation.utils.Matcher;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Configuration
public class TestData {

    public static final User adminUser = new User(1, true, "Admin", "admin@restaurants.ru", Set.of(Role.ADMIN), "admin");
    public static final User simpleUser = new User(2, true, "SimpleUser", "user@restaurants.ru", Set.of(Role.USER), "user1");

    public static final User[] users = {
            adminUser,
            simpleUser,
            new User(3, true, "TestUser1", "user1@restaurants.ru", Set.of(Role.USER), "12345"),
            new User(4, true, "TestUser2", "user2@restaurants.ru", Set.of(Role.USER), "12345"),
            new User(5, true, "TestUser3", "user3@restaurants.ru", Set.of(Role.USER), "12345")
    };
    public static final User newUser = new User("NewUser", "newuser@restaurants.ru", Set.of(Role.USER), "newuser");
    public static final User updatedUser = new User(3, true, "TestUser1", "user1u@restaurants.ru", Set.of(Role.USER), "12345");
    public static final List<User> invalidUsers = List.of();
    private static final Matcher<User> USER_MATCHER = new Matcher<>(List.of("id"));

    public static final Restaurant disabledRestaurant =
            new Restaurant(5, false, "restaurant2", "address1");
    public static final Restaurant[] restaurants = {
            new Restaurant(1, true, "restaurant1", "address1"),
            new Restaurant(2, true, "fastfood2", "address2_1"),
            new Restaurant(3, true, "fastfood2", "address2_2"),
            new Restaurant(4, true, "cafe3", "address3"),
            disabledRestaurant
    };
    public static final Restaurant newRestaurant = new Restaurant("cafe4", "address4");
    public static final Restaurant updatedRestaurant = new Restaurant(3, true, "fastfood2", "address2_3");
    public static final List<Restaurant> invalidRestaurants = List.of();
    private static final Matcher<Restaurant> RESTAURANT_MATCHER = new Matcher<>(List.of("id"));

    public static final Dish disabledDish =
            new Dish(8, false, "disabled_dish", 500.0f, restaurants[2]);
    public static final Dish[] dishes = {
            new Dish(1, true, "dish1", 300.0f, restaurants[0]),
            new Dish(2, true, "dish2", 200.0f, restaurants[0]),
            new Dish(3, true, "dish3", 200.0f, restaurants[0]),
            new Dish(4, true, "dish4", 200.0f, restaurants[1]),
            new Dish(5, true, "dish5", 300.0f, restaurants[1]),
            new Dish(6, true, "dish6", 300.0f, restaurants[2]),
            new Dish(7, true, "dish7", 300.0f, restaurants[2]),
            disabledDish
    };
    public static final Dish newDish = new Dish("newDish", 115.0f, restaurants[0]);
    public static final Dish updatedDish = new Dish(2, true, "dish2", 250.0f, restaurants[0]);
    public static final List<Dish> invalidDishes = List.of();
    public static final List<Dish> invalidUpdateDishes = List.of(
            // change of restaurant is not allowed
            new Dish(2, true, "dish2", 250.0f, restaurants[1])
    );
    private static final Matcher<Dish> DISH_MATCHER =
            new Matcher<>(List.of("id"), List.of("id"), List.of("restaurant"));

    public static final LocalDate yestarday = LocalDate.of(2024, 1, 12);
    public static final LocalDate date = LocalDate.of(2024, 1, 13);
    public static final Restaurant popularRestaurant = TestData.restaurants[2];

    public static final Menu[] menus = {
            new Menu(1, yestarday, restaurants[0], Set.of(dishes[0], dishes[1])),
            new Menu(2, date, restaurants[0], Set.of(dishes[0], dishes[1])),
            new Menu(3, date, restaurants[1], Set.of(dishes[3], dishes[4])),
    };
    public static final Menu newMenu = new Menu(null, date, restaurants[2], Set.of(dishes[5], dishes[6]));
    public static final Menu updatedMenu = new Menu(2, date, restaurants[0], Set.of(dishes[0], dishes[1]));
    public static final List<Menu> invalidMenus = List.of(
            new Menu(null, date, restaurants[2], Set.of(dishes[5], dishes[7])),
            new Menu(null, date, restaurants[2], Set.of(dishes[0], dishes[6]))
    );
    public static final List<Menu> invalidUpdateMenus = List.of(
            new Menu(2, date, restaurants[2], Set.of(dishes[6]))
    );
    private static final Matcher<Menu> MENU_MATCHER =
            new Matcher<>(List.of("id"), List.of("id", "dishes"), List.of("restaurant"));

    public static final Vote[] votes = {
            new Vote(1, date, users[1], restaurants[0]),
            new Vote(2, date, users[2], popularRestaurant),
            new Vote(3, date, users[3], popularRestaurant),
    };
    public static final Vote newVote = new Vote(null, date, users[4], popularRestaurant);
    public static final Vote updatedVote = new Vote(2, date, users[2], restaurants[3]);
    public static final List<Vote> invalidNewVotes = List.of(
            new Vote(null, date, users[3], disabledRestaurant)
    );
    public static final List<Vote> invalidUpdateVotes = List.of(
            new Vote(2, date, users[2], disabledRestaurant)
    );
    private static final Matcher<Vote> VOTE_MATCHER =
            new Matcher<>(List.of("id"), List.of("id"), List.of("restaurant", "user"));

    @Bean
    TestDataProvider<User> getUserProvider() {
        return new TestDataProvider<>(List.of(users), newUser, updatedUser,
                invalidUsers, invalidUsers, USER_MATCHER);
    }

    @Bean
    TestDataProvider<Restaurant> getRestaurantProvider() {
        return new TestDataProvider<>(List.of(restaurants), newRestaurant, updatedRestaurant,
                invalidRestaurants, invalidRestaurants, RESTAURANT_MATCHER);
    }

    @Bean
    TestDataProvider<Dish> getDishProvider() {
        return new TestDataProvider<>(List.of(dishes), newDish, updatedDish,
                invalidDishes, invalidUpdateDishes, DISH_MATCHER);
    }

    @Bean
    TestDataProvider<Menu> getMenuProvider() {
        return new TestDataProvider<>(List.of(menus), newMenu, updatedMenu,
                invalidMenus, invalidUpdateMenus, MENU_MATCHER);
    }

    @Bean
    TestDataProvider<Vote> getVoteProvider() {
        return new TestDataProvider<>(List.of(votes), newVote, updatedVote,
                invalidNewVotes, invalidUpdateVotes, VOTE_MATCHER);
    }
}
