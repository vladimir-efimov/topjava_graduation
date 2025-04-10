package ru.javawebinar.topjavagraduation.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.javawebinar.topjavagraduation.model.*;
import ru.javawebinar.topjavagraduation.topjava.MatcherFactory;

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
            new User(3, true, "TestUser1", "user1@restaurants.ru", Set.of(Role.USER), ""),
            new User(4, true, "TestUser2", "user2@restaurants.ru", Set.of(Role.USER), ""),
            new User(5, true, "TestUser3", "user3@restaurants.ru", Set.of(Role.USER), "")
    };
    public static final User newUser = new User("NewUser", "newuser@restaurants.ru", Set.of(Role.USER), "newuser");
    public static final User updatedUser = new User(3, true, "TestUser1", "user1u@restaurants.ru", Set.of(Role.USER), "");
    public static final List<User> invalidUsers = List.of();
    private static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "id");

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
    private static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "id");

    public static final Meal disabledMeal =
            new Meal(8, false, "disabled_meal", 500.0f, restaurants[2]);
    public static final Meal[] meals = {
            new Meal(1, true, "meal1", 300.0f, restaurants[0]),
            new Meal(2, true, "meal2", 200.0f, restaurants[0]),
            new Meal(3, true, "meal3", 200.0f, restaurants[0]),
            new Meal(4, true, "meal4", 200.0f, restaurants[1]),
            new Meal(5, true, "meal5", 300.0f, restaurants[1]),
            new Meal(6, true, "meal6", 300.0f, restaurants[2]),
            new Meal(7, true, "meal7", 300.0f, restaurants[2]),
            disabledMeal
    };
    public static final Meal newMeal = new Meal("newMeal", 115.0f, restaurants[0]);
    public static final Meal updatedMeal = new Meal(2, true, "meal2", 250.0f, restaurants[0]);
    public static final List<Meal> invalidMeals = List.of();
    public static final List<Meal> invalidUpdateMeals = List.of(
            // change of restaurant is not allowed
            new Meal(2, true, "meal2", 250.0f, restaurants[1])
    );
    private static final MatcherFactory.Matcher<Meal> MEAL_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "id");

    public static final LocalDate yestarday = LocalDate.of(2024, 1, 12);
    public static final LocalDate date = LocalDate.of(2024, 1, 13);
    public static final Restaurant popularRestaurant = TestData.restaurants[2];

    public static final Menu[] menus = {
            new Menu(1, yestarday, restaurants[0], Set.of(meals[0], meals[1])),
            new Menu(2, date, restaurants[0], Set.of(meals[0], meals[1])),
            new Menu(3, date, restaurants[1], Set.of(meals[3], meals[4])),
    };
    public static final Menu newMenu = new Menu(null, date, restaurants[2], Set.of(meals[5], meals[6]));
    public static final Menu updatedMenu = new Menu(2, date, restaurants[0], Set.of(meals[0], meals[1]));
    public static final List<Menu> invalidMenus = List.of(
            new Menu(null, date, restaurants[2], Set.of(meals[5], meals[7])),
            new Menu(null, date, restaurants[2], Set.of(meals[0], meals[6]))
    );
    public static final List<Menu> invalidUpdateMenus = List.of(
            new Menu(2, date, restaurants[2], Set.of(meals[6]))
    );
    private static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "id");

    public static final Vote[] votes = {
            new Vote(1, date, users[1], restaurants[0]),
            new Vote(2, date, users[2], popularRestaurant),
            new Vote(3, date, users[3], popularRestaurant),
    };
    public static final Vote newVote = new Vote(null, date, users[1], popularRestaurant);
    public static final Vote updatedVote = new Vote(2, date, users[2], restaurants[3]);
    public static final List<Vote> invalidNewVotes = List.of(
            new Vote(null, date, users[3], disabledRestaurant)
    );
    public static final List<Vote> invalidUpdateVotes = List.of(
            new Vote(2, date, users[2], disabledRestaurant)
    );
    private static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "id");

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
    TestDataProvider<Meal> getMealProvider() {
        return new TestDataProvider<>(List.of(meals), newMeal, updatedMeal,
                invalidMeals, invalidUpdateMeals, MEAL_MATCHER);
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
