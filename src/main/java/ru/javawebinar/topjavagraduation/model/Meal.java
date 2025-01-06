package ru.javawebinar.topjavagraduation.model;

public class Meal extends AbstractBaseEntity {
    private float price;
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(int id, float price, Restaurant restaurant) {
        super(id);
        this.price = price;
        this.restaurant = restaurant;
    }

    public float getPrice() {
        return price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
