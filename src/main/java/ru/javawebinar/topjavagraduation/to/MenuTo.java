package ru.javawebinar.topjavagraduation.to;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MenuTo {
    private Integer id;
    @NotNull
    private LocalDate date;
    @NotNull
    private Integer restaurantId;
    @NotNull
    private List<Integer> meals;

    public MenuTo() {
    }

    public MenuTo(Integer id, LocalDate date, Integer restaurantId, List<Integer> meals) {
        this.id = id;
        this.date = date;
        this.restaurantId = restaurantId;
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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<Integer> getMeals() {
        return meals;
    }

    public void setMeals(List<Integer> meals) {
        this.meals = meals;
    }
}
