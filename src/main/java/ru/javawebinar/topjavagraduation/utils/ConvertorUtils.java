package ru.javawebinar.topjavagraduation.utils;

import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.to.MealTo;


public class ConvertorUtils {

    public static Meal convertMealTo(MealTo mealTo) {
        var restaurant = new Restaurant();
        restaurant.setId(mealTo.getRestaurantId());
        return new Meal(mealTo.getId(), mealTo.getEnabled(), mealTo.getName(), mealTo.getPrice(), restaurant);
    }

    public static MealTo convertMeal(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.isEnabled(), meal.getPrice(), meal.getRestaurant().getId());
    }
}
