package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.service.ClockHolder;
import ru.javawebinar.topjavagraduation.service.MenuService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {

    public static final String REST_URL = "/rest/menus";
    @Autowired
    protected MenuService service;
    @Autowired
    protected ClockHolder clockHolder;

    @GetMapping(value = "/{id}")
    public Menu get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public List<Menu> filter(@RequestParam("restaurantId") Integer restaurantId,
                           @Nullable @RequestParam("date") LocalDate date) {
        if (date != null) {
            Optional<Menu> result = date.equals(getCurrentDate())
                    ? service.getTodaysMenu(restaurantId)
                    : service.findByRestaurantAndDate(restaurantId, date);
            return result.isPresent() ? List.of(result.get()) : List.of();
        } else {
            return service.findByRestaurant(restaurantId);
        }
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now(clockHolder.getClock());
    }

}
