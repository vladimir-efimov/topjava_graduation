package ru.javawebinar.topjavagraduation.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BadRequestTest extends AbstractRestControllerTest {

    @Test
    void tryGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/not_existed/1")
                        .with(userHttpBasic(TestData.simpleUser)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
