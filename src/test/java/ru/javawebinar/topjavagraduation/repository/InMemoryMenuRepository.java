package ru.javawebinar.topjavagraduation.repository;

import ru.javawebinar.topjavagraduation.model.Menu;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class InMemoryMenuRepository extends InMemoryBaseEntityRepository<Menu> implements MenuRepository {
    public List<Menu> findByRestaurant(int id) {
        return entities.values().stream()
                .filter(e -> e.getRestaurant() != null && e.getRestaurant().getId().equals(id))
                .toList();
    }

    public Optional<Menu> findByRestaurantAndDate(int id, Date date) {
        return entities.values().stream()
                .filter(e -> e.getRestaurant() != null && e.getRestaurant().getId().equals(id) &&
                        e.getDate() != null && e.getDate().equals(date))
                .findFirst();
    }
}
