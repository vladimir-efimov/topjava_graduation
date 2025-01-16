package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.repository.InMemoryUserRepository;
import ru.javawebinar.topjavagraduation.topjava.MatcherFactory;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService service;

    private static final int ADMIN_ID = 1;
    private static final int SYSTEM_USER_ID = 2;
    private static final int NOT_FOUND_USER_ID = -1;
    private static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "id");

    @BeforeEach
    void setup() {
        service = new UserService(new InMemoryUserRepository());
    }

    @Test
    void get() {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, UserService.getSystemUsers()[0]);
    }

    @Test
    void getAll() {
        assertEquals(2, service.getAll().size());
    }

    @Test
    void tryModifySystemUser() {
        User user = service.get(SYSTEM_USER_ID);
        user.setEmail("system@restaurants.ru");
        assertThrows(IllegalOperationException.class, () -> service.update(user));
    }

    @Test
    void tryDeleteSystemUser() {
        assertThrows(IllegalOperationException.class, () -> service.delete(SYSTEM_USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_USER_ID));
    }

    @Test
    void createAndUpdate() {
        User newUser = new User("newUser", "newuser@restaurantss.ru", Role.USER, "");
        User user = service.create(newUser);
        USER_MATCHER.assertMatch(newUser, user);
        var result = service.findByEmail("newuser@restaurantss.ru");
        assertTrue(result.isPresent());
        user = result.get();
        USER_MATCHER.assertMatch(newUser, user);
        user.setEmail("newuser@restaurants.ru");
        service.update(user);
        var result2 = service.findByEmail("newuser@restaurants.ru");
        assertTrue(result2.isPresent());
        User foundUser = result2.get();
        USER_MATCHER.assertMatch(user, foundUser);
    }

    @Test
    void tryUpdate() {
        assertThrows(IllegalArgumentException.class, () -> service.update(new User()));
    }

    @Test
    void checkExceptionMessage() {
        String message = "";
        try {
            service.update(new User());
        } catch(IllegalArgumentException ex) {
            message = ex.getMessage();
        }
        assertTrue(message.contains("update"));
    }

    @Test
    void tryCreate() {
        User newUser = new User("newUser", "newuser@restaurantss.ru", Role.USER, "");
        User savedUser = service.create(newUser);
        assertThrows(IllegalArgumentException.class, () -> service.create(savedUser));
    }
}
