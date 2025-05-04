package ru.javawebinar.topjavagraduation.web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.MealRepository;
import ru.javawebinar.topjavagraduation.service.MenuService;
import ru.javawebinar.topjavagraduation.to.MenuTo;

import java.util.*;

import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.buildResponseEntity;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.checkIdOnUpdate;


@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController {

    public static final String REST_URL = "/rest/admin/menus";
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MenuService service;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo to, @PathVariable int id) {
        Menu menu = convertMenuTo(to);
        checkIdOnUpdate(menu, id);
        service.update(menu);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo to) {
        Menu menu = convertMenuTo(to);
        Menu created = service.create(menu);
        return buildResponseEntity(created, REST_URL);
    }

    public Menu convertMenuTo(MenuTo menuTo) {
        var restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurantId());
        Set<Meal> meals = new HashSet<>();
        if (menuTo.getMeals() != null) {
            //todo: fix it to avoid N SQL queries
            menuTo.getMeals().forEach(id -> meals.add(mealRepository.get(id)));
        }
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant, meals);
    }

}
