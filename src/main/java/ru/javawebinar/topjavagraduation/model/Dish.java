package ru.javawebinar.topjavagraduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "dish")
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_dish",
            joinColumns = {@JoinColumn(name = "dish_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    @Hidden
    @JsonIgnore
    Set<Menu> menus;

    public Dish() {
    }

    public Dish(String name, float price, Restaurant restaurant) {
        this(null, name, price, restaurant);
    }

    public Dish(Integer id, String name, float price, Restaurant restaurant) {
        super(id, name);
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
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
