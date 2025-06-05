package ru.javawebinar.topjavagraduation.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.topjava.JacksonObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/cleanupDB.sql", "classpath:data.sql"}, config = @SqlConfig(encoding = "UTF-8"),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractRestControllerTest {

    protected static final ObjectMapper MAPPER = JacksonObjectMapper.getMapper();

    @Autowired
    protected MockMvc mockMvc;

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

}
