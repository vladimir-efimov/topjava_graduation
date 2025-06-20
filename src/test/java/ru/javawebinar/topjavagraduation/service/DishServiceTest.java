package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DishServiceTest extends AbstractServiceTest<Dish> {

    private final DishService service;

    public DishServiceTest(@Autowired DishService service, @Autowired TestDataProvider<Dish> dataProvider) {
        super(service, dataProvider);
        this.service = service;
    }

    @Test
    @Override
    void tryUpdateInvalid() {
        List<Dish> invalidEntities = dataProvider.getUpdatedInvalid();
        invalidEntities.forEach(enity -> assertThrows(IllegalOperationException.class, () -> service.update(enity)));
    }

    @Test
    @Transactional
    void findByRestaurant() {
        Integer restaurantId = dataProvider.getFirst().getRestaurant().getId();
        List<Dish> dishes = service.findByRestaurant(restaurantId);
        List<Dish> expectedDishes = dataProvider.getAll().stream()
                .filter(dish -> dish.getRestaurant().getId().equals(restaurantId)).toList();
        assertEquals(expectedDishes.size(), dishes.size());
        for (int i = 0; i < dishes.size(); i++) {
            matcher.assertMatch(expectedDishes.get(i), dishes.get(i));
        }
    }
}
