package ru.javawebinar.topjavagraduation.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Dish;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.JpaMenuRepository;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class MenuService extends AbstractBaseEntityService<Menu> {
    private final JpaMenuRepository repository;
    private final ClockHolder clockHolder;

    public MenuService(JpaMenuRepository repository, ClockHolder clockHolder) {
        super(repository);
        this.repository = repository;
        this.clockHolder = clockHolder;
    }

    @Override
    public Menu get(int id) {
        return repository.getWithDishes(id)
                .orElseThrow(() -> new NotFoundException("Menu with id " + id + " is not found"));
    }

    public List<Menu> findByRestaurant(int id) {
        return repository.getFilteredByRestaurant(id);
    }

    public Optional<Menu> findByRestaurantAndDate(int id, LocalDate date) {
        if (date.equals(getCurrentDate())) {
            return getTodaysMenu(id);
        }
        return repository.getFilteredByRestaurantAndDateWithDishes(id, date);
    }

    @Cacheable("menus")
    public Optional<Menu> getTodaysMenu(int restaurantId) {
        return repository.getFilteredByRestaurantAndDateWithDishes(restaurantId, getCurrentDate());
    }

    @Override
    protected void validateOperation(Menu menu, CrudOperation operation) {
        super.validateOperation(menu, operation);
        if (operation == CrudOperation.CREATE || operation == CrudOperation.UPDATE) {
            if (beforeToday(menu.getDate())) {
                throw new IllegalOperationException("Can't operate with menu for the past date");
            }
            for (Dish dish : menu.getDishes()) {
                if (!menu.getRestaurant().getId().equals(dish.getRestaurant().getId())) {
                    throw new IllegalArgumentException("Menu contains dish with id=" + dish.getId() +
                            " which belongs to another restaurant");
                }
            }

        }
        if (operation == CrudOperation.UPDATE) {
            Menu savedMenu = get(menu.getId());
            if (!menu.getRestaurant().equals(savedMenu.getRestaurant())) {
                throw new IllegalOperationException("Can't substitute restaurant");
            }
        }
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now(clockHolder.getClock());
    }

    private boolean beforeToday(LocalDate date) {
        return date.isBefore(getCurrentDate());
    }

    @Override
    protected void evictCache(Menu menu) {
        if (menu.getDate().equals(getCurrentDate())) {
            int restaurantId = menu.getRestaurant().getId();
            cacheManager.getCache("menus").evictIfPresent(restaurantId);
            log.debug("Evict menus cache for restaurants id " + restaurantId);
        }
    }
}
