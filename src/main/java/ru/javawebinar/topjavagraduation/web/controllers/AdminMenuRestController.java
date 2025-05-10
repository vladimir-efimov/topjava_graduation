package ru.javawebinar.topjavagraduation.web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.JpaDishRepository;
import ru.javawebinar.topjavagraduation.service.MenuService;
import ru.javawebinar.topjavagraduation.to.MenuTo;

import static ru.javawebinar.topjavagraduation.utils.ConverterUtils.convertMenuTo;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.buildResponseEntity;
import static ru.javawebinar.topjavagraduation.web.controllers.ControllerUtils.checkIdOnUpdate;


@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController {

    public static final String REST_URL = "/rest/admin/menus";
    @Autowired
    private JpaDishRepository dishRepository;
    @Autowired
    private MenuService service;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo to, @PathVariable int id) {
        Menu menu = convertMenuTo(to, dishRepository);
        checkIdOnUpdate(menu, id);
        service.update(menu);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo to) {
        Menu menu = convertMenuTo(to, dishRepository);
        Menu created = service.create(menu);
        return buildResponseEntity(created, REST_URL);
    }

}
