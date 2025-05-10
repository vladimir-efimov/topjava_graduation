package ru.javawebinar.topjavagraduation.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.*;


@Component
public class TestDataInitializer {

    @Autowired
    JpaUserRepository userRepository;

    @Autowired
    JpaRestaurantRepository restaurantRepository;

    @Autowired
    JpaDishRepository dishRepository;

    @Autowired
    JpaMenuRepository menuRepository;

    @Autowired
    JpaVoteRepository voteRepository;

    private <T extends AbstractBaseEntity> void initRepository(JpaBaseEntityRepository<T> repository,
                                                               T[] entities) {
        try {
            for (T entity : entities) {
                T copy = (T) entity.clone();
                repository.save(copy);
            }
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException("Unexpected CloneNotSupportedException: " + ex.getMessage());
        }
    }

    public void init() {
        initRepository(restaurantRepository, TestData.restaurants);
        initRepository(dishRepository, TestData.dishes);
        initRepository(menuRepository, TestData.menus);
        initRepository(userRepository, TestData.users);
        initRepository(voteRepository, TestData.votes);
    }
}
