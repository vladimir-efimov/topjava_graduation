package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.MealService;
import ru.javawebinar.topjavagraduation.to.MealTo;


@RestController
@RequestMapping(value = AdminMealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealRestController extends AbstractAdminRestController<Meal, MealTo> {
    public static final String REST_URL = "/rest/admin/meals";
    private final MealService service;

    public AdminMealRestController(MealService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @Override
    public Meal convertTo(MealTo mealTo) {
        var restaurant = new Restaurant();
        restaurant.setId(mealTo.getRestaurantId());
        return new Meal(mealTo.getId(), mealTo.getEnabled(), mealTo.getName(), mealTo.getPrice(), restaurant);
    }
}
