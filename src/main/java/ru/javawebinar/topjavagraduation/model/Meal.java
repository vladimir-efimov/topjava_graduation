package ru.javawebinar.topjavagraduation.model;

public class Meal extends AbstractManagedEntity {
    private float price;
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(String name, float price, Restaurant restaurant) {
        this(null, true, name, price, restaurant);
    }

    public Meal(Integer id, boolean enabled, String name, float price, Restaurant restaurant) {
        super(id, enabled, name);
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

    @Override
    public void assertValid() {
        if(!restaurant.isEnabled()) {
            throw new IllegalArgumentException("Meal refers to disabled restaurant");
        }
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
