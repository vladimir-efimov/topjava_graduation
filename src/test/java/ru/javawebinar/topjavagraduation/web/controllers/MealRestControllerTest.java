package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.MealService;
import ru.javawebinar.topjavagraduation.service.RestaurantService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.MealRestController.REST_URL;

public class MealRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    MealService service;
    @Autowired
    RestaurantService restaurantService;

    @Test
    void getEnabled() throws Exception {
        Restaurant restaurant = restaurantService.create((Restaurant) TestData.newRestaurant.clone());
        Meal meal = (Meal)TestData.newMeal.clone();
        meal.setRestaurant(restaurant);
        service.create(meal);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/enabled")
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }
}
