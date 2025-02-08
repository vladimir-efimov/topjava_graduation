package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.repository.MealRepository;

import java.util.List;

@Repository
public class JpaMealRepository extends JpaManagedEntityRepository<Meal> implements MealRepository {

    private final IJpaMealRepository repository;

    public JpaMealRepository(IJpaMealRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Meal> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }
}
