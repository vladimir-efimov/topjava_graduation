package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

import java.util.List;


public abstract class AbstractManagedEntityRestController<E extends AbstractManagedEntity, TO>  {

    protected final AbstractManagedEntityService<E> service;

    public AbstractManagedEntityRestController(AbstractManagedEntityService<E> service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TO> getAll() {
        return convertEntities(service.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public E get(@PathVariable int id) {
        return service.get(id);
    }

    protected List<TO> convertEntities(List<E> entities) {
        return entities.stream().map(this::convertEntity).toList();
    }

    protected abstract TO convertEntity(E entity);

    @GetMapping("/enabled")
    public List<TO> getEnabled() {
        return convertEntities(service.getEnabled());
    }

    @GetMapping("/filter")
    public List<TO> findByName(@RequestParam String name) {
        return convertEntities(service.findByName(name));
    }
}
