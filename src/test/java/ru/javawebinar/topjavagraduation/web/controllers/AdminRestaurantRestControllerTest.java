package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.data.TestData.newRestaurant;
import static ru.javawebinar.topjavagraduation.web.controllers.AdminRestaurantRestController.REST_URL;


public class AdminRestaurantRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private TestDataProvider<Restaurant> testDataProvider;

    @Autowired
    private RestaurantService service;

    @Test
    void createWithLocation() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(newRestaurant))
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant receivedRestaurant = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), Restaurant.class);
        testDataProvider.getMatcher().assertMatch(newRestaurant, receivedRestaurant);
    }

    @Test
    void tryCreateDuplicated() throws Exception {
        var restaurant = new Restaurant("cafe_dupl", "address_dupl");
        service.create((Restaurant) restaurant.clone());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(AdminRestaurantRestController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(restaurant))
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isConflict());
        ErrorInfo error = MAPPER.readValue(result.andReturn().getResponse().getContentAsString(), ErrorInfo.class);
        assertEquals(ErrorType.DATA_CONFLICT_ERROR.getName(), error.getErrorName());
        assertEquals(1, error.getDetails().length);
        assertEquals("Restaurant with the same name and address already exists", error.getDetails()[0]);
    }
}
