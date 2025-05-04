package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.lang.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = AdminUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController extends AbstractAdminRestController<User, User> {
    public static final String REST_URL = "/rest/admin/users";
    private final UserService service;

    public AdminUserRestController(UserService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @GetMapping
    public List<User> filter(@Nullable @RequestParam String name, @Nullable @RequestParam String email) {
        if (email != null) {
            Optional<User> result = service.findByEmail(email);
            if(result.isEmpty()) {
                return List.of();
            }
            User user = result.get();
            if (name != null && !user.getName().equals(name)) {
                return List.of();
            }
            return List.of(user);
        }
        if (name != null) {
            return service.findByName(name);
        }
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public User get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping("/enabled")
    public List<User> getEnabled() {
        return service.getEnabled();
    }

    @Override
    public User convertTo(User user) {
        return user;
    }
}
