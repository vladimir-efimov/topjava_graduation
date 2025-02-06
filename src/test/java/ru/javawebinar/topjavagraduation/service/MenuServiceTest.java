package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuServiceTest extends AbstractServiceTest<Menu> {

    public MenuServiceTest(@Autowired MenuService service, @Autowired TestDataProvider<Menu> dataProvider) {
        super(service, dataProvider);
        service.setDateTime(TestData.date.atStartOfDay());
    }

    @Test
    @Override
    void tryUpdateInvalid() {
        List<Menu> invalidEntities = dataProvider.getUpdatedInvalid();
        invalidEntities.forEach( enity -> assertThrows(IllegalOperationException.class, () -> service.update(enity)));
    }

}
