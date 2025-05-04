package ru.javawebinar.topjavagraduation.web.controllers;

import java.net.URI;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.service.AbstractBaseEntityService;


public abstract class AbstractAdminRestController<E extends AbstractBaseEntity, TO> {

    protected final AbstractBaseEntityService<E> service;
    private final String restUrl;

    protected AbstractAdminRestController(AbstractBaseEntityService<E> service, String restUrl) {
        this.service = service;
        this.restUrl = restUrl;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody TO to, @PathVariable int id) {
        E entity = convertTo(to);
        if (entity.getId() == null) {
            entity.setId(id);
        } else if (!entity.getId().equals(id)) {
                throw new IllegalArgumentException("Id is inconsistent");
        }
        service.update(entity);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<E> createWithLocation(@Valid @RequestBody TO to) {
        E entity = convertTo(to);
        E created = service.create(entity);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(restUrl + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    public abstract E convertTo(TO to);
}
