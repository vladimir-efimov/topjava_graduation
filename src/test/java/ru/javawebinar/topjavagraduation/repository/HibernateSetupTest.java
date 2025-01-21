package ru.javawebinar.topjavagraduation.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Role;
import ru.javawebinar.topjavagraduation.model.User;

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
}
