package ru.javawebinar.topjavagraduation.to;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class VoteTo {

    @NotNull
    private LocalDate date;

    private int userId;

    private int restaurantId;

    public VoteTo() {
    }

    public VoteTo(LocalDate date, int userId, int restaurantId) {
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
