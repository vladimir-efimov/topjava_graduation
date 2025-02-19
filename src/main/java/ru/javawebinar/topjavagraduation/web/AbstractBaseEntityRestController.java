package ru.javawebinar.topjavagraduation.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.service.AbstractBaseEntityService;

import java.net.URI;
import java.util.List;


public class AbstractBaseEntityRestController<T extends AbstractBaseEntity> {

    protected final AbstractBaseEntityService<T> service;
    private final String restUrl;

    protected AbstractBaseEntityRestController(AbstractBaseEntityService<T> service, String restUrl) {
        this.service = service;
        this.restUrl = restUrl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<T> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public T get(@PathVariable int id) {
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody T entity, @PathVariable int id) {
        service.update(entity);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> createWithLocation(@RequestBody T entity) {
        T created = service.create(entity);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(restUrl + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
