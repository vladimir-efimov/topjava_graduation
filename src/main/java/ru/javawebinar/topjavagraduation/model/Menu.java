package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(name = "menu_unique_date_restaurant_idx", columnNames = {"serve_date", "restaurant_id"})
})
public class Menu extends AbstractBaseEntity {
    @Column(name = "serve_date", nullable = false)
    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_dish",
            joinColumns = {@JoinColumn(name = "menu_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    Set<Dish> dishes;

    public Menu() {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant, Set<Dish> dishes) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public void assertValid() {
        if (!restaurant.isEnabled()) {
            throw new IllegalArgumentException("Menu refers to disabled restaurant");
        }
        for (Dish dish : dishes) {
            if (!dish.isEnabled()) {
                throw new IllegalArgumentException("Menu contains disabled dish with id=" + dish.getId());
            }
            if (!restaurant.getId().equals(dish.getRestaurant().getId())) {
                throw new IllegalArgumentException("Menu contains dish with id=" + dish.getId() +
                        " which belongs to another restaurant");
            }
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", restaurant=" + (restaurant == null ? "null" : restaurant.getName()) +
                ", dishes=" + (dishes == null ? "[]" : dishes.toString()) +
                '}';
    }
}
