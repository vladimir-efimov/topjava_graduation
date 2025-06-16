package ru.javawebinar.topjavagraduation.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;
import ru.javawebinar.topjavagraduation.to.Profile;

import java.net.URI;
import java.util.Set;

import static ru.javawebinar.topjavagraduation.web.security.SecurityUtil.getAuthorizedUserId;


@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {
    static final String REST_URL = "/rest/profile";
    private final UserService service;

    public ProfileRestController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public User get() {
        return service.get(getAuthorizedUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        service.delete(getAuthorizedUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Profile profile) {
        User user = service.get(getAuthorizedUserId());
        updateByTo(user, profile);
        service.update(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@Valid @RequestBody Profile profile) {
        User user = new User();
        updateByTo(user, profile);
        User created = service.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private static void updateByTo(User user, Profile profile) {
        user.setName(profile.getName());
        user.setEmail(profile.getEmail());
        user.setPassword(profile.getPassword());
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }
    }
}
