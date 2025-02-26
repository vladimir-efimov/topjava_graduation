package ru.javawebinar.topjavagraduation.to;

import java.time.LocalDate;
import java.util.List;

public class MenuTo {
    private Integer id;
    private LocalDate date;
    private int restaurant_id;
    private List<Integer> meals;

    public MenuTo() {
    }

    public MenuTo(Integer id, LocalDate date, int restaurant_id, List<Integer> meals) {
        this.id = id;
        this.date = date;
        this.restaurant_id = restaurant_id;
        this.meals = meals;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public List<Integer> getMeals() {
        return meals;
    }

    public void setMeals(List<Integer> meals) {
        this.meals = meals;
    }
}
