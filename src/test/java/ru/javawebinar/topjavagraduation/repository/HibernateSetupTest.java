package ru.javawebinar.topjavagraduation.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.*;
import ru.javawebinar.topjavagraduation.service.TestData;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateSetupTest {

    @Test
    void checkInit() {
        SessionFactory sessionFactory = HibernateSetup.getSessionFactory();
        assertNotNull(sessionFactory);
    }

    @Test
    void checkUserSave() {
        SessionFactory sessionFactory = HibernateSetup.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new User("newUser", "newuser@restaurants.ru", Role.USER, "12345"));
        });

        sessionFactory.inSession(session -> {
            System.out.println(session.createSelectionQuery("from User").getSingleResult());
        });
    }

    @Test
    void checkRestaurantSave() {
        SessionFactory sessionFactory = HibernateSetup.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new Restaurant("cafe1", "address1"));
        });

        sessionFactory.inSession(session -> {
            System.out.println(session.createSelectionQuery("from Restaurant").getSingleResult());
        });
    }

    @Test
    void checkMealSave() {
        SessionFactory sessionFactory = HibernateSetup.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            Restaurant restaurant = new Restaurant("cafe2", "address2");
            session.persist(restaurant);
            session.persist(new Meal("meal1", 250.0f, restaurant));
        });

        sessionFactory.inSession(session -> {
            System.out.println(session.createSelectionQuery("from Meal").getSingleResult());
        });
    }

    @Test
    void checkVoteSave() {
        SessionFactory sessionFactory = HibernateSetup.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            Restaurant restaurant = new Restaurant("cafe3", "address3");
            User user = new User("newUser2", "newuser2@restaurants.ru", Role.USER, "12345");
            session.persist(restaurant);
            session.persist(user);
            session.persist(new Vote(user, restaurant));
        });

        sessionFactory.inSession(session -> {
            System.out.println(session.createSelectionQuery("from Vote").getSingleResult());
        });
    }

    @Test
    void checkMenuSave() {
        SessionFactory sessionFactory = HibernateSetup.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            Restaurant restaurant = new Restaurant("cafe5", "address5");
            session.persist(restaurant);
            Meal meal11 = new Meal("meal11", 250.0f, restaurant);
            session.persist(meal11);
            Meal meal12 = new Meal("meal12", 250.0f, restaurant);
            session.persist(meal12);
            Set<Meal> meals = Set.of(meal11, meal12);
            Menu menu = new Menu(null, TestData.date, restaurant, meals);
            session.persist(menu);
        });

        sessionFactory.inSession(session -> {
            System.out.println(session.createSelectionQuery("from Menu").getSingleResult());
        });
    }

}
