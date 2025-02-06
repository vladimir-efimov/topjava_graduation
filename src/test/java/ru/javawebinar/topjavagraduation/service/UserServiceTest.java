package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractServiceTest<User> {

    private final UserService service;
    private static final int SYSTEM_USER_ID = 2;

    public UserServiceTest(@Autowired  UserService service, @Autowired TestDataProvider<User> dataProvider) {
        super(service, dataProvider);
        this.service = service;
    }

    @BeforeEach
    void setup() {
        super.setup();
        service.init();
    }

    @Test
    void tryUpdateSystemUser() {
        User user = service.get(SYSTEM_USER_ID);
        user.setEmail("system@restaurants.ru");
        assertThrows(IllegalOperationException.class, () -> service.update(user));
    }
}
