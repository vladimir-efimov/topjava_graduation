package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractAdminManagedEntityRestController<Restaurant, Restaurant> {
    public static final String REST_URL = "/rest/admin/restaurants";

    public AdminRestaurantRestController(AbstractManagedEntityService<Restaurant> service) {
        super(service, REST_URL);
    }

    @Override
    public Restaurant convertTo(Restaurant restaurant) {
        return restaurant;
    }
}
