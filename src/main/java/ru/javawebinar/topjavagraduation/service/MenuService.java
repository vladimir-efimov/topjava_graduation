package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.MenuRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MenuService extends AbstractBaseEntityService<Menu> {
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Menu> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }

    public Optional<Menu> findByRestaurantAndDate(int id, Date date) {
        return findByRestaurantAndDate(id, date);
    }
}
