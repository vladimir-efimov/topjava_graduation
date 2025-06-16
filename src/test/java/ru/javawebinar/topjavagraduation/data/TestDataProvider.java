package ru.javawebinar.topjavagraduation.data;

import java.util.List;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.utils.Matcher;


public class TestDataProvider<T extends AbstractBaseEntity> {

    private final List<T> entities;
    private final T newEntity;
    private final T updatedEntity;
    private final List<T> invalidNewEntities;
    private final List<T> invalidUpdateEntities;
    private final Matcher<T> matcher;

    protected TestDataProvider(List<T> entities, T newEntity, T updatedEntity,
                               List<T> invalidNewEntities, List<T> invalidUpdateEntities, Matcher<T> matcher) {
        this.entities = entities;
        this.newEntity = newEntity;
        this.updatedEntity = updatedEntity;
        this.invalidNewEntities = invalidNewEntities;
        this.invalidUpdateEntities = invalidUpdateEntities;
        this.matcher = matcher;
    }

    protected TestDataProvider(List<T> entities, T newEntity, T updatedEntity, Matcher<T> matcher) {
        this(entities, newEntity, updatedEntity, List.of(), List.of(), matcher);
    }

    public T getNew() {
        try {
            return (T) newEntity.clone();
        } catch(CloneNotSupportedException ex) {
            throw new IllegalStateException("Failed to clone " + newEntity);
        }
    }

    public T getFirst() {
        return entities.get(0);
    }

    public List<T> getAll() {
        return entities;
    }

    public T getUpdated() {
        return updatedEntity;
    }

    public List<T> getNewInvalid() {
        return invalidNewEntities;
    }

    public List<T> getUpdatedInvalid() {
        return invalidUpdateEntities;
    }

    public Matcher<T> getMatcher() {
        return matcher;
    }
}
