package ru.javawebinar.topjavagraduation.utils;

import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.MealRepository;
import ru.javawebinar.topjavagraduation.to.MealTo;
import ru.javawebinar.topjavagraduation.to.MenuTo;

import java.util.HashSet;
import java.util.Set;


public class ConverterUtils {

    public static Meal convertMealTo(MealTo mealTo) {
        var restaurant = new Restaurant();
        restaurant.setId(mealTo.getRestaurantId());
        return new Meal(mealTo.getId(), mealTo.getEnabled(), mealTo.getName(), mealTo.getPrice(), restaurant);
    }

    public static MealTo convertMeal(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.isEnabled(), meal.getPrice(), meal.getRestaurant().getId());
    }

    public static Menu convertMenuTo(MenuTo menuTo, MealRepository mealRepository) {
        var restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurantId());
        Set<Meal> meals = new HashSet<>(mealRepository.findByIds(menuTo.getMeals()));
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant, meals);
    }
}
