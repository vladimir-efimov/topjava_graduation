package ru.javawebinar.topjavagraduation.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;


@RestController
@RequestMapping(value = UserRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController extends AbstractManagedEntityRestController<User> {
    public static final String REST_URL = "/rest/users";
    private final UserService service;

    public UserRestController(UserService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @GetMapping("/find")
    public User findByEmail(@RequestParam String email) {
        return service.findByEmail(email).orElse(null);
    }
}
