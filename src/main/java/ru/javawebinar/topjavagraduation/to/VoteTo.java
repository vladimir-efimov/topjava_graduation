package ru.javawebinar.topjavagraduation.to;

import jakarta.validation.constraints.NotNull;


public class VoteTo {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer restaurantId;

    public VoteTo() {
    }

    public VoteTo(Integer userId, Integer restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
