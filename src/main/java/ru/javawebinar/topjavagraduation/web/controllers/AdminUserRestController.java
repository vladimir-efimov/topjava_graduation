package ru.javawebinar.topjavagraduation.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;

import java.util.List;
import java.util.Optional;

import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.buildResponseEntity;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.checkIdOnUpdate;


@RestController
@RequestMapping(value = AdminUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {
    public static final String REST_URL = "/rest/admin/users";
    private final UserService service;

    public AdminUserRestController(UserService service) {
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        checkIdOnUpdate(user, id);
        service.update(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        User created = service.create(user);
        return buildResponseEntity(created, REST_URL);
    }
}
