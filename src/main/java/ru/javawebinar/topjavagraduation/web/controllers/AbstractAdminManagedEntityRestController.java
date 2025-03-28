package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

import java.util.List;

public abstract class AbstractAdminManagedEntityRestController<E extends AbstractManagedEntity, TO> extends AbstractAdminBaseEntityRestController<E, TO> {

    protected final AbstractManagedEntityService<E> service;

    public AbstractAdminManagedEntityRestController(AbstractManagedEntityService<E> service, String restUrl) {
        super(service, restUrl);
        this.service = service;
    }

    @GetMapping("/enabled")
    public List<E> getEnabled() {
        return service.getEnabled();
    }

    @GetMapping("/filter")
    public List<E> findByName(@RequestParam String name) {
        return service.findByName(name);
    }
}
