package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;
import ru.javawebinar.topjavagraduation.to.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.ProfileRestController.REST_URL;


public class ProfileRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private UserService service;

    @Autowired
    private TestDataProvider<User> testDataProvider;

    @Test
    void register() throws Exception {
        Profile profile = new Profile(TestData.newUser.getName(),
                "register@restaurants.ru", TestData.newUser.getPassword());
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(profile)))
                .andDo(print())
                .andExpect(status().isCreated());
        User registeredUser = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        assertNotNull(registeredUser.getId());
        assertEquals(profile.getName(), registeredUser.getName());
        assertEquals(profile.getEmail(), registeredUser.getEmail());
    }

    @Test
    void loginWithNewUser() throws Exception {
        User newUser = (User) TestData.newUser.clone();
        newUser.setEmail("loginWithNewUser@restaurants.ru");
        service.create(newUser);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(newUser)))
                .andDo(print())
                .andExpect(status().isOk());
        User registeredUser = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        testDataProvider.getMatcher().assertMatch(newUser, registeredUser);
    }

    @Test
    void tryLoginWithDisabledUser() throws Exception {
        User newUser = (User) TestData.newUser.clone();
        newUser.setEmail("loginWithNewUser@restaurants.ru");
        newUser.setEnabled(false);
        service.create(newUser);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(newUser)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
