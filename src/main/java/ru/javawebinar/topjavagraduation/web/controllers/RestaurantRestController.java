package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.RestaurantService;

import java.util.List;


@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String REST_URL = "/rest/restaurants";
    @Autowired
    RestaurantService service;

    @GetMapping(value = "/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping("/enabled")
    public List<Restaurant> getEnabled() {
        return service.getEnabled();
    }

    @GetMapping
    public List<Restaurant> find(@Nullable @RequestParam String name) {
        if (name != null) {
            return service.findByName(name);
        }
        return service.getAll();
    }
}
