package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.service.TestClockHolder;
import ru.javawebinar.topjavagraduation.service.UserService;
import ru.javawebinar.topjavagraduation.service.VoteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class VoteRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    VoteService service;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @Autowired
    TestClockHolder clockHolder;

    @AfterEach
    void restoreClockHolder() {
        clockHolder.setDateTime(LocalDateTime.now());
    }

    @Test
    void vote() throws Exception {
        int restaurantId = restaurantService.create(new Restaurant("Cafe1", "Vote street")).getId();
        mockMvc.perform(MockMvcRequestBuilders.post(VoteRestController.REST_URL)
                        .param("restaurantId", Integer.toString(restaurantId))
                        .with(userHttpBasic(TestData.simpleUser)))
                .andDo(print())
                .andExpect(status().isCreated());
        Optional<Vote> result = service.findByUserAndDate(TestData.simpleUser.getId(), LocalDate.now());
        assertTrue(result.isPresent());
        assertEquals(restaurantId, result.get().getRestaurant().getId());
    }

    @Test
    void revoke() throws Exception {
        int restaurantId = restaurantService.create(new Restaurant("Cafe2", "Vote street")).getId();
        clockHolder.setDateTime(LocalDate.now().atStartOfDay());
        service.create(TestData.simpleUser.getId(), restaurantId);
        mockMvc.perform(MockMvcRequestBuilders.delete(VoteRestController.REST_URL + "/todays-vote")
                        .with(userHttpBasic(TestData.simpleUser)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Vote> result = service.findByUserAndDate(TestData.simpleUser.getId(), LocalDate.now());
        assertTrue(result.isEmpty());
    }

    @Test
    void tryRevoke() throws Exception {
        Optional<Vote> result = service.findByUserAndDate(TestData.simpleUser.getId(), LocalDate.now());
        if (result.isPresent()) {
            service.revoke(TestData.simpleUser.getId());
        }
        mockMvc.perform(MockMvcRequestBuilders.delete(VoteRestController.REST_URL + "/todays-vote")
                        .with(userHttpBasic(TestData.simpleUser)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
