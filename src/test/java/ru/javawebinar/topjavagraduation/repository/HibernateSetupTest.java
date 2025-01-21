package ru.javawebinar.topjavagraduation.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.*;

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
}
