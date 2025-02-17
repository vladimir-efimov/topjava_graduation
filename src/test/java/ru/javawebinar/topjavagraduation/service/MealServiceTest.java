package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealServiceTest extends AbstractServiceTest<Meal>{

    private final MealService service;

    public MealServiceTest(@Autowired MealService service, @Autowired TestDataProvider<Meal> dataProvider) {
        super(service, dataProvider);
        this.service = service;
    }

    @Test
    @Override
    void tryUpdateInvalid() {
        List<Meal> invalidEntities = dataProvider.getUpdatedInvalid();
        invalidEntities.forEach( enity -> assertThrows(IllegalOperationException.class, () -> service.update(enity)));
    }

    @Test
    void findByRestaurant() {
        Integer restaurantId = dataProvider.getFirst().getRestaurant().getId();
        List<Meal> meals = service.findByRestaurant(restaurantId);
        List<Meal> expectedMeals = dataProvider.getAll().stream()
                .filter(meal -> meal.getRestaurant().getId().equals(restaurantId)).toList();
        assertEquals(expectedMeals.size(), meals.size());
        for(int i = 0; i < meals.size(); i++ ) {
            matcher.assertMatch(expectedMeals.get(i), meals.get(i));
        }
    }
}
