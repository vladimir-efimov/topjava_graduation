package ru.javawebinar.topjavagraduation.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractManagedEntityRestController<Restaurant> {
    public static final String REST_URL = "/rest/restaurants";

    public RestaurantRestController(AbstractManagedEntityService<Restaurant> service) {
        super(service, REST_URL);
    }
}
