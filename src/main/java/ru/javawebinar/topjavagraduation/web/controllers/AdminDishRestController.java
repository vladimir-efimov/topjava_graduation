package ru.javawebinar.topjavagraduation.web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.repository.JpaRestaurantRepository;
import ru.javawebinar.topjavagraduation.service.DishService;
import ru.javawebinar.topjavagraduation.to.DishTo;
import ru.javawebinar.topjavagraduation.utils.ConverterUtils;

import java.util.List;

import static ru.javawebinar.topjavagraduation.utils.ConverterUtils.convertDishTo;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.buildResponseEntity;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.checkIdOnUpdate;


@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {
    public static final String REST_URL = "/rest/admin/dishes";
    @Autowired
    private DishService service;
    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public List<DishTo> filter(@Nullable @RequestParam("restaurantId") Integer id) {
        List<Dish> dishes = id != null ? service.findByRestaurant(id) : service.getAll();
        return dishes.stream().map(ConverterUtils::convertDish).toList();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo to, @PathVariable int id) {
        Dish dish = convertDishTo(to, restaurantRepository);
        checkIdOnUpdate(dish, id);
        service.update(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo to) {
        Dish dish = convertDishTo(to, restaurantRepository);
        Dish created = service.create(dish);
        return buildResponseEntity(created, REST_URL);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
