package ru.javawebinar.topjavagraduation.to;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class VoteTo {

    @NotNull
    private LocalDate date;

    private int restaurantId;

    public VoteTo() {
    }

    public VoteTo(LocalDate date, int restaurantId) {
        this.date = date;
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
