package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMenuRepository extends JpaBaseEntityRepository<Menu> implements MenuRepository {

    private final IJpaMenuRepository repository;

    public JpaMenuRepository(IJpaMenuRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Menu> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }

    @Override
    public List<Menu> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    @Override
    public Optional<Menu> findByRestaurantAndDate(int id, LocalDate date) {
        return repository.findByRestaurantAndDate(id, date);
    }
}
