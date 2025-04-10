package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.MealRepository;
import ru.javawebinar.topjavagraduation.service.MenuService;
import ru.javawebinar.topjavagraduation.to.MenuTo;

import java.util.*;

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractAdminRestController<Menu, MenuTo> {

    public static final String REST_URL = "/rest/admin/menus";
    @Autowired
    private MealRepository mealRepository;

    public AdminMenuRestController(MenuService service) {
        super(service, REST_URL);
    }

    @Override
    public Menu convertTo(MenuTo menuTo) {
        var restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurantId());
        Set<Meal> meals = new HashSet<>();
        if (menuTo.getMeals() != null) {
            menuTo.getMeals().forEach(id -> meals.add(mealRepository.get(id)));
        }
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant, meals);
    }
}
