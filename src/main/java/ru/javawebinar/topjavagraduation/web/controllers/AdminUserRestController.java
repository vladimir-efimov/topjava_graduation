package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;

import java.util.List;


@RestController
@RequestMapping(value = AdminUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController extends AbstractAdminRestController<User, User> {
    public static final String REST_URL = "/rest/admin/users";
    private final UserService service;

    public AdminUserRestController(UserService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @GetMapping("/find")
    public User findByEmail(@RequestParam String email) {
        return service.findByEmail(email).orElse(null);
    }

    @Override
    public User convertTo(User user) {
        return user;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping("/enabled")
    public List<User> getEnabled() {
        return service.getEnabled();
    }

    @GetMapping("/filter")
    public List<User> findByName(@RequestParam String name) {
        return service.findByName(name);
    }
}
