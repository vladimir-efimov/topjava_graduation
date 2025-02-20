package ru.javawebinar.topjavagraduation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.data.TestData.newUser;
import static ru.javawebinar.topjavagraduation.web.UserRestController.REST_URL;


@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/test.xml"
})
public class UserRestControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestDataProvider<User> testDataProvider;

    @Autowired
    private UserService service;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
//                .apply(springSecurity())
                .build();
    }

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
    @Disabled // todo: double check behavior in case of incorrect request
    // Should controller process NotFoundException?
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
        User user = service.create((User)newUser.clone());
        user.setEmail("updated_email@restaurants.ru");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isNoContent());
        testDataProvider.getMatcher().assertMatch(user, service.get(user.getId()));
    }
}
