package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.DishService;
import ru.javawebinar.topjavagraduation.service.MenuService;
import ru.javawebinar.topjavagraduation.service.RestaurantService;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.MenuRestController.REST_URL;


public class MenuRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    DishService dishService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    MenuService menuService;

    @Test
    void getAll() throws Exception {
        Restaurant restaurant = restaurantService.create((Restaurant) TestData.newRestaurant.clone());
        Dish dish = (Dish) TestData.newDish.clone();
        dish.setRestaurant(restaurant);
        Dish createdDish = dishService.create(dish);
        menuService.create(new Menu(null, LocalDate.now(), restaurant, Set.of(createdDish)));

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }
}
