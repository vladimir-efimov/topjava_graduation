package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.model.Restaurant;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class RestaurantServiceTest extends AbstractServiceTest<Restaurant> {

    private final RestaurantService service;

    @Autowired
    public RestaurantServiceTest(RestaurantService service, TestDataProvider<Restaurant> dataProvider) {
        super(service, dataProvider);
        this.service = service;
    }

    @Test
    void delete() {
        int id = dataProvider.getFirst().getId();
        service.delete(id);
        assertThrows(NotFoundException.class, () -> service.get(id));
    }
}
