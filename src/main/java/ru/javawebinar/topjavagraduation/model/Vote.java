package ru.javawebinar.topjavagraduation.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {
    private LocalDate date;
    private User user;
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant) {
        this(null, LocalDate.now(), user, restaurant);
    }

    public Vote(Integer id, LocalDate date, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void assertValid() {
        if(!restaurant.isEnabled()) {
            throw new IllegalArgumentException("Vote refers to disabled restaurant");
        }
    }

    @Override
    public String toString() {
        return "Vote{" +
                "date=" + date +
                ", user=" + (user == null ? "null" : user.getName()) +
                ", restaurant=" + (restaurant == null ? "null" : restaurant.getName()) +
                '}';
    }
}
