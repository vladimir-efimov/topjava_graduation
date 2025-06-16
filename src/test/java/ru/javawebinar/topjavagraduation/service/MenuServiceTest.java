package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class MenuServiceTest extends AbstractServiceTest<Menu> {

    private final MenuService service;
    private final TestClockHolder clockHolder;

    @Autowired
    public MenuServiceTest(MenuService service, TestDataProvider<Menu> dataProvider,
                           TestClockHolder clockHolder) {
        super(service, dataProvider);
        this.service = service;
        this.clockHolder = clockHolder;
        clockHolder.setDateTime(TestData.date.atStartOfDay());
    }

    @AfterEach
    void restoreClockHolder() {
        clockHolder.setDateTime(LocalDateTime.now());
    }

    @Test
    @Override
    void tryUpdateInvalid() {
        List<Menu> invalidEntities = dataProvider.getUpdatedInvalid();
        invalidEntities.forEach(enity -> assertThrows(IllegalOperationException.class, () -> service.update(enity)));
    }

    @Test
    void delete() {
        Menu newMenu = service.create(dataProvider.getNew());
        Menu obtainedMenu = service.get(newMenu.getId());
        assertEquals(newMenu, obtainedMenu);
        service.delete(newMenu.getId());
        assertThrows(NotFoundException.class, () -> service.get(newMenu.getId()));
    }
}
