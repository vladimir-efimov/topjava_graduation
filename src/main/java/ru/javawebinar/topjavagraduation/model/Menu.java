package ru.javawebinar.topjavagraduation.model;

import java.time.LocalDate;
import java.util.Set;

public class Menu extends AbstractBaseEntity {
    LocalDate date;
    Restaurant restaurant;
    Set<Meal> meals;

    public Menu () {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant, Set<Meal> meals) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.meals = meals;
    }

    public LocalDate getDate() {
        return date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public void assertValid() {
        if(!restaurant.isEnabled()) {
            throw new IllegalArgumentException("Meal refers to disabled restaurant");
        }
        for(Meal meal: meals) {
            if(!meal.isEnabled()) {
                throw new IllegalArgumentException("Menu contains disabled meal with id=" + meal.getId());
            }
            if(!restaurant.getId().equals(meal.getRestaurant().getId())) {
                throw new IllegalArgumentException("Menu contains meal with id=" + meal.getId() +
                        " which belongs to another restaurant");
            }
        }
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
