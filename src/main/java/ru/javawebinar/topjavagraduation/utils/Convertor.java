package ru.javawebinar.topjavagraduation.utils;

import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.to.MealTo;

public class Convertor {

    public static Meal convertMealTo(MealTo mealTo) {
        var restaurant = new Restaurant();
        restaurant.setId(mealTo.getRestaurant_id());
        return new Meal(mealTo.getId(), mealTo.isEnabled(), mealTo.getName(), mealTo.getPrice(), restaurant);
    }
}
