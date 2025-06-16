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

    @GetMapping
    public List<Restaurant> filter(@Nullable @RequestParam String name, @Nullable @RequestParam String address) {
        if (name != null) {
            if (address != null) {
                var result = service.findByNameAndAddress(name, address);
                return result.isEmpty() ? List.of() : List.of(result.get());
            }
            return service.findByName(name);
        }
        if (address != null) {
            return service.findByAddress(address);
        }
        return service.getAll();
    }
}
