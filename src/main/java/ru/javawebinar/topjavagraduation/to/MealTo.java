package ru.javawebinar.topjavagraduation.to;

public class MealTo {

    private Integer id;
    private String name;
    private boolean enabled;
    private float price;
    private int restaurantId;

    public MealTo() {
    }

    public MealTo(Integer id, String name, boolean enabled, float price, int restaurantId) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.price = price;
        this.restaurantId = restaurantId;
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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
