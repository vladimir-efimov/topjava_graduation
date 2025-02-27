package ru.javawebinar.topjavagraduation.to;


public class VoteTo {
    private int userId;
    private int restaurantId;

    public VoteTo() {
    }

    public VoteTo(int userId, int restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
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
