package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.*;

import java.time.LocalDate;
import java.util.Set;

public class TestData {

    public static final User[] users = {
            new User(1, true, "Admin", "admin@restaurants.ru", Set.of(Role.ADMIN), ""),
            new User(2, true, "SimpleUser", "user@restaurants.ru", Set.of(Role.USER), ""),
            new User(3, true, "TestUser1", "user1@restaurants.ru", Set.of(Role.USER), ""),
            new User(4, true, "TestUser2", "user2@restaurants.ru", Set.of(Role.USER), "")
    };

    public static final Restaurant[] restaurants = {
            new Restaurant(1, true, "restaurant1", "address1"),
            new Restaurant(2, true, "fastfood2", "address2_1"),
            new Restaurant(3, true, "fastfood2", "address2_2"),
            new Restaurant(4, true, "cafe3", "address3"),
            new Restaurant(5, false, "restaurant2", "address1")
    };

    public static final Meal[] meals = {
            new Meal(1, true, "meal1", 300.0f, restaurants[0]),
            new Meal(2, true, "meal2", 200.0f, restaurants[0]),
            new Meal(3, true, "meal3", 200.0f, restaurants[0]),
            new Meal(4, true, "meal4", 200.0f, restaurants[1]),
            new Meal(5, true, "meal5", 300.0f, restaurants[1]),
            new Meal(6, true, "meal6", 300.0f, restaurants[2]),
            new Meal(7, true, "meal7", 300.0f, restaurants[2]),
            new Meal(8, false, "disabled_meal", 500.0f, restaurants[2]),
    };

    public static final LocalDate yestarday = LocalDate.of(2024, 1, 12);
    public static final LocalDate date = LocalDate.of(2024, 1, 13);
    public static final Restaurant popularRestaurant = TestData.restaurants[2];

    public static final Menu[] menus = {
            new Menu(1, yestarday, restaurants[0], Set.of(meals[0], meals[1])),
            new Menu(2, date, restaurants[0], Set.of(meals[0], meals[1])),
            new Menu(3, date, restaurants[1], Set.of(meals[3], meals[4])),
    };

    public static final Menu newMenu = new Menu(null, date, restaurants[2], Set.of(meals[5], meals[6]));
    public static final Menu[] invalidMenus = {
            new Menu(null, date, restaurants[2], Set.of(meals[5], meals[7])),
            new Menu(null, date, restaurants[2], Set.of(meals[0], meals[6])),
    };
    public static final Menu invalidUpdateMenu = new Menu(2, date, restaurants[2], Set.of(meals[6]));

    public static final Vote[] votes = {
            new Vote(1, date, TestData.users[1], TestData.restaurants[0]),
            new Vote(2, date, TestData.users[2], popularRestaurant),
            new Vote(3, date, TestData.users[3], popularRestaurant),
    };
}
