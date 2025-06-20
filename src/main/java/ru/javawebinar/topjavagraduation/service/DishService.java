package ru.javawebinar.topjavagraduation.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.repository.JpaDishRepository;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;

import java.util.List;


@Service
public class DishService extends AbstractNamedEntityService<Dish> {
    private final JpaDishRepository repository;

    public DishService(JpaDishRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Dish get(int id) {
        return repository.getWithRestaurant(id)
                .orElseThrow(() -> new NotFoundException("Dish with id " + id + " is not found"));
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

    @Override
    protected void evictCache(Dish dish) {
        cacheManager.getCache("menus").evictIfPresent(dish.getRestaurant().getId());
        log.debug("Evict menus cache for restaurants with id " + dish.getRestaurant().getId());
    }
}
