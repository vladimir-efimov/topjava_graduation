package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.utils.Matcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql(scripts = {"classpath:db/cleanupDB.sql", "classpath:data.sql"}, config = @SqlConfig(encoding = "UTF-8"),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractServiceTest<T extends AbstractBaseEntity> {
    protected final AbstractBaseEntityService<T> service;
    protected final TestDataProvider<T> dataProvider;
    protected final Matcher<T> matcher;

    protected AbstractServiceTest(AbstractBaseEntityService<T> service, TestDataProvider<T> dataProvider) {
        this.service = service;
        this.dataProvider = dataProvider;
        matcher = dataProvider.getMatcher();
    }

    @Test
    void get() {
        T entity = service.get(1);
        matcher.assertMatch(dataProvider.getFirst(), entity);
    }

    @Test
    void getAll() {
        List<T> entities = service.getAll();
        List<T> expectedEntities = dataProvider.getAll();
        matcher.assertMatch(expectedEntities, entities);
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
    void tryCreateWithNotNullId() {
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
