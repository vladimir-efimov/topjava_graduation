package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.MealService;
import ru.javawebinar.topjavagraduation.to.MealTo;

import java.util.List;


@RestController
@RequestMapping(value = AdminMealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealRestController extends AbstractAdminRestController<Meal, MealTo> {
    public static final String REST_URL = "/rest/admin/meals";
    private final MealService service;

    public AdminMealRestController(MealService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public List<MealTo> find(@Nullable @RequestParam("restaurantId") Integer id) {
        List<Meal> meals = id != null ? service.findByRestaurant(id) : service.getAll();
        return meals.stream().map(this::convertEntity).toList();
    }

    @GetMapping("/enabled")
    public List<MealTo> getEnabled() {
        return service.getEnabled().stream().map(this::convertEntity).toList();
    }

    @Override
    public Meal convertTo(MealTo mealTo) {
        var restaurant = new Restaurant();
        restaurant.setId(mealTo.getRestaurantId());
        return new Meal(mealTo.getId(), mealTo.getEnabled(), mealTo.getName(), mealTo.getPrice(), restaurant);
    }

    private MealTo convertEntity(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.isEnabled(), meal.getPrice(), meal.getRestaurant().getId());
    }

}
