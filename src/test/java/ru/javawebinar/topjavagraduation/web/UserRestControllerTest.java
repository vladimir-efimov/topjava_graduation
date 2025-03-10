package ru.javawebinar.topjavagraduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.data.TestData.newUser;
import static ru.javawebinar.topjavagraduation.web.UserRestController.REST_URL;


public class UserRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    private TestDataProvider<User> testDataProvider;

    @Autowired
    private UserService service;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                 .andExpect(USER_MATCHER.contentJson(user1)
        ;
    }

    @Test
    void getEnabled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/enabled"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/0"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
        User receivedUser = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        testDataProvider.getMatcher().assertMatch(newUser, receivedUser);
    }

    @Test
    void update() throws Exception {
        User user = service.create((User) newUser.clone());
        user.setEmail("updated_email@restaurants.ru");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isNoContent());
        testDataProvider.getMatcher().assertMatch(user, service.get(user.getId()));
    }

    @Test
    void tryUpdateSystemUser() throws Exception {
        User systemUser = service.get(1);
        systemUser.setEmail("updated_email@restaurants.ru");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + systemUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(systemUser)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void tryCreateWithNotNullId() throws Exception {
        User user = (User) newUser.clone();
        user.setId(999);
        user.setEmail(user.getEmail());
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user)))
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
                        .content(MAPPER.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isConflict());
        ErrorInfo error = MAPPER.readValue(result.andReturn().getResponse().getContentAsString(), ErrorInfo.class);
        assertEquals(ErrorType.DATA_CONFLICT_ERROR, error.getErrorType());
        assertEquals(1, error.getDetails().length);
        assertEquals("Email is already used for another user", error.getDetails()[0]);
    }

    @Test
    void tryCreateWithNullName() throws Exception {
        User user = (User) newUser.clone();
        user.setName(null);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        ErrorInfo error = MAPPER.readValue(result.andReturn().getResponse().getContentAsString(), ErrorInfo.class);
        assertEquals(ErrorType.VALIDATION_ERROR, error.getErrorType());
        assertEquals(1, error.getDetails().length);
        assertEquals("'name' must not be blank", error.getDetails()[0]);
    }
}
