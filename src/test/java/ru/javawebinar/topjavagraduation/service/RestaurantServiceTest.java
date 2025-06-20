package ru.javawebinar.topjavagraduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Restaurant;


public class RestaurantServiceTest extends AbstractServiceTest<Restaurant> {

    @Autowired
    public RestaurantServiceTest(RestaurantService service, TestDataProvider<Restaurant> dataProvider) {
        super(service, dataProvider);
    }
}
