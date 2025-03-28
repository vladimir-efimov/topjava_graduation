package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.MealRepository;
import ru.javawebinar.topjavagraduation.service.MenuService;
import ru.javawebinar.topjavagraduation.to.MenuTo;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractAdminBaseEntityRestController<Menu, MenuTo> {

    public static final String REST_URL = "/rest/admin/menus";
    private final MenuService service;
    @Autowired
    private MealRepository mealRepository;

    public AdminMenuRestController(MenuService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @Override
    public Menu convertTo(MenuTo menuTo) {
        var restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurantId());
        Set<Meal> meals = new HashSet<>();
        if( menuTo.getMeals() != null) {
            menuTo.getMeals().forEach(id -> meals.add(mealRepository.get(id)));
        }
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant, meals);
    }

    @GetMapping("/find")
    public List<Menu> find(@Nullable @RequestParam("restaurant_id") Integer id,
                           @Nullable @RequestParam("date") LocalDate date) {
        if(id != null) {
            if (date != null) {
                Optional<Menu> result = service.findByRestaurantAndDate(id, date);
                return result.isPresent() ? List.of(result.get()) : List.of();
            } else {
                return service.findByRestaurant(id);
            }
        } else {
            return date != null ? service.findByDate(date) : service.getAll();
        }
    }
}
