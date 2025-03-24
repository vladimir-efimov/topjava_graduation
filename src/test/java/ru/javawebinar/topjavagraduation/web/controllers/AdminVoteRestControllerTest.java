package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.service.VoteService;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorInfo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminVoteRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    VoteService service;

    @Test
    void getEndVotingTime() throws Exception {
        String responseString = mockMvc.perform(MockMvcRequestBuilders.get(AdminVoteRestController.REST_URL + "/end_voting_time")
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().replace("\"","");
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("H:mm:ss");
        assertEquals(service.getEndVotingTime(), LocalTime.parse(responseString, parser));
    }

    @Test
    void getUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(AdminVoteRestController.REST_URL + "/end_voting_time"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getWithIncorrectRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(AdminVoteRestController.REST_URL + "/end_voting_time")
                        .with(userHttpBasic(TestData.simpleUser)))
                .andDo(print())
                .andExpect(status().isForbidden());
        // todo: what if handle such types of exceptions
    }
}
