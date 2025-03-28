package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.MealService;
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
    MealService mealService;

    @Test
    void createWithLocation() throws Exception {
        var restaurant = restaurantService.create(new Restaurant("New Restaurant", "Restaurant street"));
        var meal1 = mealService.create(new Meal("meal1", 100.0f, restaurant));
        var meal2 = mealService.create(new Meal("meal2", 120.0f, restaurant));

        var menuTo = new MenuTo(null, LocalDate.now(), restaurant.getId(), List.of(meal1.getId(), meal2.getId()));
        System.out.println(MAPPER.writeValueAsString(menuTo));
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(menuTo))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isCreated());
        Menu menu = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), Menu.class);
        assertEquals(menuTo.getDate(), menu.getDate());
        assertNotNull(menu.getId());
        assertNotNull(menu.getMeals());
        assertEquals(2, menu.getMeals().size());
    }
}
