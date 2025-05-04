package ru.javawebinar.topjavagraduation.web.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;


public class ControllerUtils {

    public static void checkIdOnUpdate(AbstractBaseEntity entity, int id) {
        if (entity.getId() == null) {
            entity.setId(id);
        } else if (!entity.getId().equals(id)) {
                throw new IllegalArgumentException("Id is inconsistent");
        }
    }

    public static <E extends AbstractBaseEntity> ResponseEntity<E> buildResponseEntity(E entity, String restUrl) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(restUrl + "/{id}")
                .buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(entity);
    }
}
