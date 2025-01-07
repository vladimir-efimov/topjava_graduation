package ru.javawebinar.topjavagraduation.model;

import java.util.Date;
import java.util.Set;

public class Menu extends AbstractBaseEntity {
    Date date;
    Restaurant restaurant;
    Set<Meal> meals;

    public Menu () {
    }

    public Menu(Integer id, Date date, Restaurant restaurant, Set<Meal> meals) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.meals = meals;
    }

    public Date getDate() {
        return date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", restaurant=" + (restaurant == null ? "null" : restaurant.getName()) +
                ", meals=" + (meals == null ? "[]" : meals.toString()) +
                '}';
    }
}
