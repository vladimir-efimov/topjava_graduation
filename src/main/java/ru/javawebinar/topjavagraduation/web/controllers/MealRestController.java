package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.service.MealService;
import ru.javawebinar.topjavagraduation.to.MealTo;

import java.util.List;


@RestController
@RequestMapping(value = MealRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractManagedEntityRestController<Meal, MealTo> {
    public static final String REST_URL = "/rest/meals";
    private final MealService service;

    public MealRestController(MealService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/restaurant-meals")
    public List<MealTo> findByRestaurant(@RequestParam("restaurant_id") int id) {
        return convertEntities(service.findByRestaurant(id));
    }

    @Override
    protected MealTo convertEntity(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.isEnabled(), meal.getPrice(), meal.getRestaurant().getId());
    }
}
