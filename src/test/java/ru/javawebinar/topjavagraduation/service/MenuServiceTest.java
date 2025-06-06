package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class MenuServiceTest extends AbstractServiceTest<Menu> {

    private final TestClockHolder clockHolder;

    @Autowired
    public MenuServiceTest(MenuService service, TestDataProvider<Menu> dataProvider,
                           TestClockHolder clockHolder) {
        super(service, dataProvider);
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

}
