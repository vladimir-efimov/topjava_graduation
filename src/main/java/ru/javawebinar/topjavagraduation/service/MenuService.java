package ru.javawebinar.topjavagraduation.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
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
        return repository.getWithDishesAndRestaurant(id)
                .orElseThrow(() -> new NotFoundException("Menu with id " + id + " is not found"));
    }

    public List<Menu> findByRestaurant(int id) {
        return repository.getFilteredByRestaurantWithDishes(id);
    }

    public List<Menu> findByDate(LocalDate date) {
        return repository.getFilteredByDateWithDishesAndRestaurant(date);
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
    public void update(Menu menu) {
        super.update(menu);
        if (menu.getDate().equals(getCurrentDate())) {
            evictCache(menu);
        }
    }

    public void delete(int id) {
        Menu menu = get(id);
        validateOperation(menu, CrudOperation.DELETE);
        repository.delete(menu);
        if (menu.getDate().equals(getCurrentDate())) {
            evictCache(menu);
        }
    }

    @Override
    protected void validateOperation(Menu menu, CrudOperation operation) {
        super.validateOperation(menu, operation);
        if (operation == CrudOperation.CREATE || operation == CrudOperation.UPDATE) {
            if (beforeToday(menu.getDate())) {
                throw new IllegalOperationException("Can't operate with menu for the past date");
            }
        }
        if (operation == CrudOperation.UPDATE) {
            Menu savedMenu = get(menu.getId());
            if (!savedMenu.getRestaurant().equals(menu.getRestaurant())) {
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

    @CacheEvict(value = "menus", key = "#menu.restaurantId")
    private void evictCache(Menu menu) {
    }
}
