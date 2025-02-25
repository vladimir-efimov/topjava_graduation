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


public abstract class AbstractBaseEntityRestController<E extends AbstractBaseEntity, TO> {

    protected final AbstractBaseEntityService<E> service;
    private final String restUrl;

    protected AbstractBaseEntityRestController(AbstractBaseEntityService<E> service, String restUrl) {
        this.service = service;
        this.restUrl = restUrl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<E> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public E get(@PathVariable int id) {
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody TO to, @PathVariable int id) {
        E entity = convertTo(to);
        service.update(entity);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<E> createWithLocation(@RequestBody TO to) {
        E entity = convertTo(to);
        E created = service.create(entity);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(restUrl + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    public abstract E convertTo(TO to);
}
