package ru.javawebinar.topjavagraduation.data;

import java.util.List;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.topjava.MatcherFactory;

public class TestDataProvider<T extends AbstractBaseEntity> {

    private final List<T> entities;
    private final T newEntity;
    private final T updatedEntity;
    private final List<T> invalidNewEntities;
    private final List<T> invalidUpdateEntities;
    private final MatcherFactory.Matcher<T> matcher;

    protected TestDataProvider(List<T> entities, T newEntity, T updatedEntity,
                               List<T> invalidNewEntities, List<T> invalidUpdateEntities, MatcherFactory.Matcher<T> matcher) {
        this.entities = entities;
        this.newEntity = newEntity;
        this.updatedEntity = updatedEntity;
        this.invalidNewEntities = invalidNewEntities;
        this.invalidUpdateEntities = invalidUpdateEntities;
        this.matcher = matcher;
    }

    public T getNew() {
        return newEntity;
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

    public MatcherFactory.Matcher<T> getMatcher() {
        return matcher;
    }
}
