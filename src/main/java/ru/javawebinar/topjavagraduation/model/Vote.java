package ru.javawebinar.topjavagraduation.model;

import java.util.Date;

public class Vote extends AbstractBaseEntity {
    private Date date;
    private User user;
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Date date, User user, Restaurant restaurant) {
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
