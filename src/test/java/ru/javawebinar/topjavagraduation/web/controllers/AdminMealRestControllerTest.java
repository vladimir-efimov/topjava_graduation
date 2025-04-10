package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.to.MealTo;
import ru.javawebinar.topjavagraduation.data.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.AdminMealRestController.REST_URL;


public class AdminMealRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    void createWithLocation() throws Exception {
        var restaurant = restaurantService.create(new Restaurant("NewRestaurant", "Restaurant street"));
        var mealto = new MealTo(null, "meal1", true, 100.0f, restaurant.getId());
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(mealto))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isCreated());
        Meal meal = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), Meal.class);
        assertEquals(mealto.getName(), meal.getName());
        assertEquals(mealto.getPrice(), meal.getPrice());
        assertNotNull(meal.getId());
    }

}
