package ru.javawebinar.topjavagraduation.web.controllers;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjavagraduation.web.controllers.MenuRestController.REST_URL;


public class MenuRestControllerTest extends AbstractRestControllerTest {

    @Autowired
    TestDataProvider<Menu> dataProvider;

    @Test
    void getForRestaurant() throws Exception {

        ResultActions action =  mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(TestData.adminUser))
                        .param("restaurantId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List menus = MAPPER.readValue(action.andReturn().getResponse().getContentAsString(), List.class);
        List<Menu> expectedMenus = Arrays.stream(TestData.menus)
                .filter(menu -> menu.getRestaurant().getId() == 1).toList();
        assertEquals(expectedMenus.size(), menus.size());
    }
}
