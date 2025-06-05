package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.VoteRestController.REST_URL;


public class BadRequestTest extends AbstractRestControllerTest {

    @Test
    void tryGet() throws Exception {
        mockMvc.getDispatcherServlet().setThrowExceptionIfNoHandlerFound(true);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/not_existed/1")
                        .with(userHttpBasic(TestData.simpleUser)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
