package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dish", uniqueConstraints = {
        @UniqueConstraint(name = "dish_unique_name_restaurant_idx", columnNames = {"name", "restaurant_id"})
})
public class Dish extends AbstractManagedEntity {

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, float price, Restaurant restaurant) {
        this(null, true, name, price, restaurant);
    }

    public Dish(Integer id, boolean enabled, String name, float price, Restaurant restaurant) {
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
        if (!restaurant.isEnabled()) {
            throw new IllegalArgumentException("Dish refers to disabled restaurant");
        }
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", enabled=" + enabled +
                ", restaurant=" + restaurant +
                '}';
    }
}
