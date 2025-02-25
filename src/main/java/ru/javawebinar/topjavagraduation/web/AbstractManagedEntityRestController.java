package ru.javawebinar.topjavagraduation.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

import java.util.List;

public abstract class AbstractManagedEntityRestController<E extends AbstractManagedEntity, TO> extends AbstractBaseEntityRestController<E, TO> {

    protected final AbstractManagedEntityService<E> service;

    public AbstractManagedEntityRestController(AbstractManagedEntityService<E> service, String restUrl) {
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
