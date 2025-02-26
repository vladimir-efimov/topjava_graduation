package ru.javawebinar.topjavagraduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = MenuRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractBaseEntityRestController<Menu, MenuTo> {

    public static final String REST_URL = "/rest/menus";
    private final MenuService service;
    @Autowired
    private MealRepository mealRepository;

    public MenuRestController(MenuService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @Override
    public Menu convertTo(MenuTo menuTo) {
        var restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurant_id());
        Set<Meal> meals = new HashSet<>();
        if( menuTo.getMeals() != null) {
            menuTo.getMeals().forEach(id -> meals.add(mealRepository.get(id)));
        }
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant, meals);
    }

    @GetMapping("/restaurant_menus")
    public List<Menu> findByRestaurant(@RequestParam("restaurant_id") int id) {
        return service.findByRestaurant(id);
    }

    // todo: add findByRestaurantAndDate(), double check how to deal with Optional
}
