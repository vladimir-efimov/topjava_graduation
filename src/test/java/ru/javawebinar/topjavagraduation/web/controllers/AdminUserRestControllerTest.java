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
import ru.javawebinar.topjavagraduation.validation.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.ErrorType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.data.TestData.newUser;
import static ru.javawebinar.topjavagraduation.web.controllers.AdminUserRestController.REST_URL;


public class AdminUserRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private TestDataProvider<User> testDataProvider;

    @Autowired
    private UserService service;

    @Test
    void get() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/1")
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User receivedUser = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        testDataProvider.getMatcher().assertMatch(service.get(1), receivedUser);
    }

    @Test
    void getEnabled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/enabled")
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/0")
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createWithLocation() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(newUser))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isCreated());
        User receivedUser = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        testDataProvider.getMatcher().assertMatch(newUser, receivedUser);
        assertNotNull(receivedUser.getId());
    }

    @Test
    void update() throws Exception {
        User user = service.create((User) newUser.clone());
        user.setEmail("updated_email@restaurants.ru");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isNoContent());
        testDataProvider.getMatcher().assertMatch(user, service.get(user.getId()));
    }

    @Test
    void updateWithNullId() throws Exception {
        User user = (User) newUser.clone();
        Integer userId = service.create(user).getId();
        User modifiedUser = new User(user.getName(),"updated_email2@restaurants.ru", user.getRoles(), user.getPassword());
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(modifiedUser))
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isNoContent());
        testDataProvider.getMatcher().assertMatch(modifiedUser, service.get(userId));
    }

    @Test
    void tryUpdateSystemUser() throws Exception {
        User systemUser = (User) TestData.adminUser.clone();
        systemUser.setEmail("updated_email@restaurants.ru");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + systemUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(systemUser))
                        .with(userHttpBasic(TestData.adminUser)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tryCreateWithNotNullId() throws Exception {
        User user = (User) newUser.clone();
        user.setId(999);
        user.setEmail(user.getEmail());
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user))
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tryCreateWithDuplicatedEmail() throws Exception {
        User systemUser = service.get(1);
        User user = (User) newUser.clone();
        user.setEmail(systemUser.getEmail());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user))
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isConflict());
        ErrorInfo error = MAPPER.readValue(result.andReturn().getResponse().getContentAsString(), ErrorInfo.class);
        assertEquals(ErrorType.DATA_CONFLICT_ERROR.getName(), error.getErrorName());
        assertEquals(ErrorType.DATA_CONFLICT_ERROR.getMessage(), error.getMessage());
        assertEquals(1, error.getDetails().length);
        assertEquals("Email is already used for another user", error.getDetails()[0]);
    }

    @Test
    void tryCreateWithNullName() throws Exception {
        User user = (User) newUser.clone();
        user.setName(null);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user))
                        .with(userHttpBasic(TestData.adminUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        ErrorInfo error = MAPPER.readValue(result.andReturn().getResponse().getContentAsString(), ErrorInfo.class);
        assertEquals(ErrorType.VALIDATION_ERROR.getName(), error.getErrorName());
        assertEquals(1, error.getDetails().length);
        assertEquals("'name' must not be blank", error.getDetails()[0]);
    }
}
