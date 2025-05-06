package ru.javawebinar.topjavagraduation.web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;
import ru.javawebinar.topjavagraduation.service.MealService;
import ru.javawebinar.topjavagraduation.to.MealTo;
import ru.javawebinar.topjavagraduation.utils.ConverterUtils;

import java.util.List;

import static ru.javawebinar.topjavagraduation.utils.ConverterUtils.convertMealTo;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.buildResponseEntity;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.checkIdOnUpdate;


@RestController
@RequestMapping(value = AdminMealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealRestController {
    public static final String REST_URL = "/rest/admin/meals";
    @Autowired
    private MealService service;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public List<MealTo> filter(@Nullable @RequestParam("restaurantId") Integer id) {
        List<Meal> meals = id != null ? service.findByRestaurant(id) : service.getAll();
        return meals.stream().map(ConverterUtils::convertMeal).toList();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MealTo to, @PathVariable int id) {
        Meal meal = convertMealTo(to, restaurantRepository);
        checkIdOnUpdate(meal, id);
        service.update(meal);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody MealTo to) {
        Meal meal = convertMealTo(to, restaurantRepository);
        Meal created = service.create(meal);
        return buildResponseEntity(created, REST_URL);
    }

    @GetMapping("/enabled")
    public List<MealTo> getEnabled() {
        return service.getEnabled().stream().map(ConverterUtils::convertMeal).toList();
    }

}
