package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.service.AbstractBaseEntityService;

import java.util.List;

public abstract class AbstractBaseEntityRestController<E extends AbstractBaseEntity, TO> {
    protected final AbstractBaseEntityService<E> service;

    public AbstractBaseEntityRestController(AbstractBaseEntityService<E> service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
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
}
