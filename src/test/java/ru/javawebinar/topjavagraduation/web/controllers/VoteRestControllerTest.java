package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.RestaurantService;
import ru.javawebinar.topjavagraduation.service.UserService;
import ru.javawebinar.topjavagraduation.service.VoteService;
import ru.javawebinar.topjavagraduation.to.VoteTo;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class VoteRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    VoteService service;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @Test
    void createWithLocation() throws Exception {
        service.setEndVotingTime(LocalTime.MAX);
        var restaurant = restaurantService.create(new Restaurant("Popular Restaurant", "Restaurant street"));
        var user = userService.get(1);
        var voteTo = new VoteTo(user.getId(), restaurant.getId());
        System.out.println(MAPPER.writeValueAsString(voteTo));
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(VoteRestController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(voteTo)))
                .andExpect(status().isCreated());
        Vote vote = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), Vote.class);
        assertNotNull(vote.getId());
        assertEquals(voteTo.getRestaurantId(), vote.getRestaurant().getId());
        assertEquals(voteTo.getUserId(), vote.getUser().getId());
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VoteRestController.REST_URL + "/0"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(VoteRestController.REST_URL + "/0"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
