package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;

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
        new Restaurant(2, true,"fastfood2", "address2_1"),
        new Restaurant(3, true,"fastfood2","address2_2"),
        new Restaurant(4, true,"cafe3","address3")
    };

    public static final LocalDate date = LocalDate.of(2024, 1, 13);
    public static final Restaurant popularRestaurant = TestData.restaurants[2];

    public static final Vote[] votes = {
        new Vote(date, TestData.users[1], TestData.restaurants[0]),
        new Vote(date, TestData.users[2], popularRestaurant),
        new Vote(date, TestData.users[3], popularRestaurant),
    };
}
