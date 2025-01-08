package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.InMemoryUserRepository;

public class UserServiceTest {

    UserService service;

    @BeforeEach
    void setup() {
        service = new UserService(new InMemoryUserRepository());
        System.out.println(service.getAll());
    }

    @Test
    void getAll() {
        Assertions.assertEquals(2, service.getAll().size());
    }

    @Test
    void tryModifySystemUser() {
        User user = new User();
        user.setEmail("admin@restaurants.ru");
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(user));
    }

    @Test
    void tryDeleteSystemUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(2));
    }

}
