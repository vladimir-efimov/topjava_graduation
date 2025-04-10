package ru.javawebinar.topjavagraduation.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class InMemoryMenuRepository extends InMemoryBaseEntityRepository<Menu> implements MenuRepository {
    @Override
    public List<Menu> findByRestaurant(int id) {
        return entities.values().stream()
                .filter(e -> e.getRestaurant() != null && e.getRestaurant().getId().equals(id))
                .toList();
    }

    @Override
    public List<Menu> findByDate(LocalDate date) {
        return entities.values().stream()
                .filter(e -> e.getRestaurant() != null && e.getDate().equals(date))
                .toList();
    }

    @Override
    public Optional<Menu> findByRestaurantAndDate(int id, LocalDate date) {
        return entities.values().stream()
                .filter(e -> e.getRestaurant() != null && e.getRestaurant().getId().equals(id) &&
                        e.getDate() != null && e.getDate().equals(date))
                .findFirst();
    }
}
