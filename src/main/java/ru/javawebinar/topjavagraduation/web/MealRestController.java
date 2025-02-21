package ru.javawebinar.topjavagraduation.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.service.MealService;

import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractManagedEntityRestController<Meal> {
    public static final String REST_URL = "/rest/meals";
    private final MealService service;

    public MealRestController(MealService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @GetMapping("/restaurant_meals")
    public List<Meal> findByRestaurant(@RequestParam("restaurant_id") int id) {
        return service.findByRestaurant(id);
    }

}
