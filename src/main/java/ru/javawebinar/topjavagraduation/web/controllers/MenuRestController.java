package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.service.MenuService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = MenuRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractBaseEntityRestController<Menu> {

    public static final String REST_URL = "/rest/menus";
    private final MenuService service;

    public MenuRestController(MenuService service) {
        super(service);
        this.service = service;
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
