package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.repository.JpaDishRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.util.List;

@Service
public class DishService extends AbstractManagedEntityService<Dish> {
    private final JpaDishRepository repository;

    public DishService(JpaDishRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Dish get(int id) {
        return repository.getWithRestaurant(id);
    }

    public List<Dish> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }

    @Override
    protected void validateOperation(Dish dish, CrudOperation operation) {
        super.validateOperation(dish, operation);
        if (operation == CrudOperation.UPDATE) {
            Dish savedDish = get(dish.getId());
            if (!savedDish.getRestaurant().equals(dish.getRestaurant())) {
                throw new IllegalOperationException("Can't substitute restaurant");
            }
        }
    }
}
