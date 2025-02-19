package ru.javawebinar.topjavagraduation.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjavagraduation.model.AbstractManagedEntity;
import ru.javawebinar.topjavagraduation.service.AbstractManagedEntityService;

import java.util.List;

public class AbstractManagedEntityRestController<T extends AbstractManagedEntity> extends AbstractBaseEntityRestController<T> {

    protected final AbstractManagedEntityService<T> service;

    public AbstractManagedEntityRestController(AbstractManagedEntityService<T> service, String restUrl) {
        super(service, restUrl);
        this.service = service;
    }

    @GetMapping("/enabled")
    public List<T> getEnabled() {
        return service.getEnabled();
    }

    @GetMapping("/filter")
    public List<T> findByName(@RequestParam String name) {
        return service.findByName(name);
    }
}
