package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ru.javawebinar.topjavagraduation.data.TestDataInitializer;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.topjava.MatcherFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/inmemory.xml",
        "classpath:spring/test.xml",
})
public abstract class AbstractServiceTest<T extends AbstractBaseEntity> {
    protected final AbstractBaseEntityService<T> service;
    protected final TestDataProvider<T> dataProvider;
    protected final MatcherFactory.Matcher<T> matcher;

    @Autowired
    private TestDataInitializer dataInitializer;

    protected AbstractServiceTest(AbstractBaseEntityService<T> service, TestDataProvider<T> dataProvider) {
        this.service = service;
        this.dataProvider = dataProvider;
        matcher = dataProvider.getMatcher();
    }

    @BeforeEach
    void setup() {
        dataInitializer.init();
    }

    @Test
    void get() {
        T entity = service.get(1);
        matcher.assertMatch(entity, dataProvider.getFirst());
    }

    @Test
    void getAll() {
        List<T> entities = service.getAll();
        List<T> expectedEntities = dataProvider.getAll();
        assertEquals(expectedEntities.size(), entities.size());
        for (int i = 0; i < entities.size(); i++) {
            matcher.assertMatch(expectedEntities.get(i), entities.get(i));
        }
    }

    @Test
    void create() {
        T newEntity = dataProvider.getNew();
        T savedEntity = service.create(newEntity);
        assertNotNull(savedEntity.getId());
        matcher.assertMatch(newEntity, savedEntity);
    }

    @Test
    void update() {
        T updatedEntity = dataProvider.getUpdated();
        service.update(updatedEntity);
        T entity = service.get(updatedEntity.getId());
        matcher.assertMatch(updatedEntity, entity);
    }

    @Test
    void tryUpdateNew() {
        assertThrows(IllegalArgumentException.class, () -> service.update(dataProvider.getNew()));
    }

    @Test
    void tryCreate() {
        T newEntity = dataProvider.getNew();
        T savedEntity = service.create(newEntity);
        assertThrows(IllegalArgumentException.class, () -> service.create(savedEntity));
    }

    @Test
    void tryCreateInvalid() {
        List<T> invalidEntities = dataProvider.getNewInvalid();
        invalidEntities.forEach(enity -> assertThrows(IllegalArgumentException.class, () -> service.create(enity)));
    }

    @Test
    void tryUpdateInvalid() {
        List<T> invalidEntities = dataProvider.getUpdatedInvalid();
        invalidEntities.forEach(enity -> assertThrows(IllegalArgumentException.class, () -> service.update(enity)));
    }

}
