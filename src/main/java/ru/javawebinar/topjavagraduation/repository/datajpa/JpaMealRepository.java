package ru.javawebinar.topjavagraduation.repository.datajpa;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.MealRepository;

import java.util.List;

@Repository
public class JpaMealRepository extends JpaManagedEntityRepository<Meal> implements MealRepository {

    private final IJpaMealRepository repository;
    @Autowired
    EntityManager em;

    public JpaMealRepository(IJpaMealRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Meal> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }

    @Override
    @Transactional
    public Meal save(Meal meal) {
        Meal mealCopy = new Meal(meal.getId(), meal.isEnabled(), meal.getName(),meal.getPrice(),
                em.getReference(Restaurant.class, meal.getRestaurant().getId()));
        return repository.save(mealCopy);
    }
}
