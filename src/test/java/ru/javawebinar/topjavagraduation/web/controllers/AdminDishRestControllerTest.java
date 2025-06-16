package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.DishService;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.to.DishTo;
import ru.javawebinar.topjavagraduation.data.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.AdminDishRestController.REST_URL;


public class AdminDishRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    DishService dishService;

    @Test
    void createWithLocation() throws Exception {
        var restaurant = restaurantService.create(new Restaurant("NewRestaurant", "Restaurant street"));
        var dishto = new DishTo(null, "dish1", 100.0f, restaurant.getId());
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dishto))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isCreated());
        Dish dish = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), Dish.class);
        assertEquals(dishto.getName(), dish.getName());
        assertEquals(dishto.getPrice(), dish.getPrice());
        assertNotNull(dish.getId());
    }

    @Test
    void update() throws Exception {
        var restaurant = restaurantService.create(new Restaurant("NewRestaurant", "Restaurant street"));
        var dish = dishService.create(new Dish("dish1", 100.0f, restaurant));
        Integer dishId = dish.getId();
        var dishto = new DishTo(dishId, "dish1", 111.0f, restaurant.getId());
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + dishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dishto))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isNoContent());
        Dish updated = dishService.get(dishId);
        assertEquals(dishto.getName(), updated.getName());
        assertEquals(dishto.getPrice(), updated.getPrice());
        assertEquals(dishId, updated.getId());
    }
}
