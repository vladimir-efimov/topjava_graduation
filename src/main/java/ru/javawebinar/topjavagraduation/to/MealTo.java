package ru.javawebinar.topjavagraduation.to;

public class MealTo {

    private Integer id;
    private String name;
    private boolean enabled;
    private float price;
    private int restaurant_id;

    public MealTo() {
    }

    public MealTo(Integer id, String name, boolean enabled, float price, int restaurant_id) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.price = price;
        this.restaurant_id = restaurant_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
