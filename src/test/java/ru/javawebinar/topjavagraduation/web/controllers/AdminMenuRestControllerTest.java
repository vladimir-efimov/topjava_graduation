package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.DishService;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.AdminMenuRestController.REST_URL;


public class AdminMenuRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    @Test
    void createWithLocation() throws Exception {
        var restaurant = restaurantService.create(new Restaurant("New Restaurant", "Restaurant street"));
        var dish1 = dishService.create(new Dish("dish1", 100.0f, restaurant));
        var dish2 = dishService.create(new Dish("dish2", 120.0f, restaurant));

        var menuTo = new MenuTo(null, LocalDate.now(), restaurant.getId(), List.of(dish1.getId(), dish2.getId()));
        System.out.println(MAPPER.writeValueAsString(menuTo));
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(menuTo))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isCreated());
        Menu menu = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), Menu.class);
        assertEquals(menuTo.getDate(), menu.getDate());
        assertNotNull(menu.getId());
        assertNotNull(menu.getDishes());
        assertEquals(2, menu.getDishes().size());
    }
}
