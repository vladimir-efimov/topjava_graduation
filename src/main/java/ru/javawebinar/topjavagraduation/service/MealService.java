package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Meal;
import ru.javawebinar.topjavagraduation.repository.JpaMealRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.util.List;

@Service
public class MealService extends AbstractManagedEntityService<Meal> {
    private final JpaMealRepository repository;

    public MealService(JpaMealRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Meal get(int id) {
        return repository.getWithRestaurant(id);
    }

    public List<Meal> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }

    @Override
    protected void validateOperation(Meal meal, CrudOperation operation) {
        super.validateOperation(meal, operation);
        if (operation == CrudOperation.UPDATE) {
            Meal savedMeal = get(meal.getId());
            if (!savedMeal.getRestaurant().equals(meal.getRestaurant())) {
                throw new IllegalOperationException("Can't substitute restaurant");
            }
        }
    }
}
