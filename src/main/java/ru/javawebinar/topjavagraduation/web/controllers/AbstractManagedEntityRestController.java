package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

import java.util.List;


public abstract class AbstractManagedEntityRestController<E extends AbstractManagedEntity, TO> extends AbstractBaseEntityRestController<E, TO> {

    protected final AbstractManagedEntityService<E> service;

    public AbstractManagedEntityRestController(AbstractManagedEntityService<E> service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/enabled")
    public List<TO> getEnabled() {
        return convertEntities(service.getEnabled());
    }

    @GetMapping("/filter")
    public List<TO> findByName(@RequestParam String name) {
        return convertEntities(service.findByName(name));
    }
}
