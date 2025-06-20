package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.model.Vote;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest extends AbstractServiceTest<User> {

    private final UserService service;
    private final VoteService voteService;
    private final RestaurantService restaurantService;
    private final TestClockHolder clockHolder;
    private static final int SYSTEM_USER_ID = 2;

    @Autowired
    public UserServiceTest(UserService service, VoteService voteService, RestaurantService restaurantService,
                           TestClockHolder clockHolder, TestDataProvider<User> dataProvider) {
        super(service, dataProvider);
        this.service = service;
        this.voteService = voteService;
        this.restaurantService = restaurantService;
        this.clockHolder = clockHolder;
    }

    @Test
    void tryUpdateSystemUser() {
        User user = service.get(SYSTEM_USER_ID);
        user.setEmail("user@restaurants.ru");
        assertThrows(IllegalOperationException.class, () -> service.update(user));
    }

    @Test
    @Override
    void delete() {
        User newUser = service.create(dataProvider.getNew()); // use new user instead of first as first is system user
        User obtainedUser = service.get(newUser.getId());
        assertEquals(newUser, obtainedUser);
        service.delete(newUser.getId());
        assertThrows(NotFoundException.class, () -> service.get(newUser.getId()));
    }

    @Test
    void deleteAfterVoting() {
        User newUser = service.create(dataProvider.getNew());
        User obtainedUser = service.get(newUser.getId());
        assertEquals(newUser, obtainedUser);
        clockHolder.setDateTime(TestData.date.atStartOfDay());
        try {
            voteService.create(new Vote(newUser, restaurantService.get(1)));
        } finally {
            clockHolder.setDateTime(LocalDateTime.now());
        }
        service.delete(newUser.getId());
        assertThrows(NotFoundException.class, () -> service.get(newUser.getId()));
    }
}
