package ru.javawebinar.topjavagraduation.model;

import java.time.LocalDateTime;

public class Vote {
    private LocalDateTime dateTime;
    private User user;
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(LocalDateTime dateTime, User user, Restaurant restaurant) {
        this.dateTime = dateTime;
        this.user = user;
        this.restaurant = restaurant;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
