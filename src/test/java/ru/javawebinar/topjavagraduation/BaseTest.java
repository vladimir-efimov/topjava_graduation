package ru.javawebinar.topjavagraduation;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BaseTest {

    @Test
    void checkPrint() {
        System.out.println(new User());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        System.out.println(new User(1,true, "v", "v@mail.ru", roles, "user"));
        System.out.println(new Meal());
        System.out.println(new Restaurant());
        System.out.println(new Vote());
        System.out.println(new Menu());
        Set<Meal> meals = new HashSet<>();
        meals.add(new Meal());
        System.out.println(new Menu(1, new Date(2025, 1, 7), new Restaurant(), meals));
        System.out.println("PASSED");
    }
}
