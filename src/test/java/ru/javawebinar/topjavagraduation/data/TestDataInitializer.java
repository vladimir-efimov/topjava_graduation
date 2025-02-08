package ru.javawebinar.topjavagraduation.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;
import ru.javawebinar.topjavagraduation.repository.*;

@Component
public class TestDataInitializer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    VoteRepository voteRepository;

    private <T extends AbstractBaseEntity> void initRepository(BaseEntityRepository<T> repository,
                                                               T[] entities) {
        try {
            for (T entity : entities) {
                T copy = (T) entity.clone();
                copy.setId(null);
                repository.save(copy);
            }
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException("Unexpected CloneNotSupportedException: " + ex.getMessage());
        }
    }

    public void init() {
        voteRepository.clean();
        userRepository.clean();
        menuRepository.clean();
        mealRepository.clean();
        restaurantRepository.clean();
        initRepository(restaurantRepository, TestData.restaurants);
        initRepository(mealRepository, TestData.meals);
        initRepository(menuRepository, TestData.menus);
        initRepository(userRepository, TestData.users);
        initRepository(voteRepository, TestData.votes);
    }
}
