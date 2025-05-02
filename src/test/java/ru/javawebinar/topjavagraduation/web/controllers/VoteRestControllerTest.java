package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.service.TestClockHolder;
import ru.javawebinar.topjavagraduation.service.UserService;
import ru.javawebinar.topjavagraduation.service.VoteService;

import java.time.LocalDate;
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

    @Test
    void vote() throws Exception {
        int restaurantId = restaurantService.create(new Restaurant("Cafe1", "Vote street")).getId();
        User user = userService.findByEmail(TestData.simpleUser.getEmail()).get();
        mockMvc.perform(MockMvcRequestBuilders.post(VoteRestController.REST_URL)
                        .param("restaurantId", Integer.toString(restaurantId))
                        .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isCreated());
        Optional<Vote> result = service.findByUserAndDate(user.getId(), LocalDate.now());
        assertTrue(result.isPresent());
        assertEquals(restaurantId, result.get().getRestaurant().getId());
    }

    @Test
    void revoke() throws Exception {
        int restaurantId = restaurantService.create(new Restaurant("Cafe2", "Vote street")).getId();
        clockHolder.setDateTime(LocalDate.now().atStartOfDay());
        User user = userService.findByEmail(TestData.simpleUser.getEmail()).get();
        service.create(user.getId(), restaurantId);
        mockMvc.perform(MockMvcRequestBuilders.delete(VoteRestController.REST_URL + "/todays-vote")
                        .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Vote> result = service.findByUserAndDate(user.getId(), LocalDate.now());
        assertTrue(result.isEmpty());
    }

    @Test
    void tryRevoke() throws Exception {
        User user = userService.findByEmail(TestData.simpleUser.getEmail()).get();
        Optional<Vote> result = service.findByUserAndDate(user.getId(), LocalDate.now());
        if (result.isPresent()) {
            service.revoke(user.getId());
        }
        mockMvc.perform(MockMvcRequestBuilders.delete(VoteRestController.REST_URL + "/todays-vote")
                        .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
