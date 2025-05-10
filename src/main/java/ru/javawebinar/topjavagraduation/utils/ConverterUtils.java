package ru.javawebinar.topjavagraduation.utils;

import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.JpaDishRepository;
import ru.javawebinar.topjavagraduation.repository.JpaRestaurantRepository;
import ru.javawebinar.topjavagraduation.to.DishTo;
import ru.javawebinar.topjavagraduation.to.MenuTo;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

import java.util.HashSet;
import java.util.Set;


public class ConverterUtils {

    public static Dish convertDishTo(DishTo dishTo, JpaRestaurantRepository restaurantRepository) {
        var restaurant = restaurantRepository.findById(dishTo.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Can't find restaurant with id " + dishTo.getRestaurantId()));
        return new Dish(dishTo.getId(), dishTo.getEnabled(), dishTo.getName(), dishTo.getPrice(), restaurant);
    }

    public static DishTo convertDish(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.isEnabled(), dish.getPrice(), dish.getRestaurant().getId());
    }

    public static Menu convertMenuTo(MenuTo menuTo, JpaDishRepository dishRepository) {
        var restaurant = new Restaurant(); //todo: read from repository?
        restaurant.setId(menuTo.getRestaurantId());
        Set<Dish> dishes = new HashSet<>(dishRepository.findAllById(menuTo.getDishes()));
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant, dishes);
    }
}
