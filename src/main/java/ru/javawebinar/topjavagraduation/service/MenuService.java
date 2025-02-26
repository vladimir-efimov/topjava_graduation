package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.MenuRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService extends AbstractBaseEntityService<Menu> {
    private final MenuRepository repository;
    private LocalDateTime testingPurposeDate = null;


    public MenuService(MenuRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Menu> findByRestaurant(int id) {
        return repository.findByRestaurant(id);
    }

    public List<Menu> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public Optional<Menu> findByRestaurantAndDate(int id, LocalDate date) {
        return repository.findByRestaurantAndDate(id, date);
    }

    @Override
    protected void validateOperation(Menu menu, CrudOperation operation) {
        super.validateOperation(menu, operation);
        if(operation == CrudOperation.CREATE || operation == CrudOperation.UPDATE) {
            if (beforeToday(menu.getDate())) {
                throw new IllegalOperationException("Can't operate with menu for the past date");
            }
        }
        if(operation == CrudOperation.UPDATE) {
            Menu savedMenu = get(menu.getId());
            if(!savedMenu.getRestaurant().equals(menu.getRestaurant())) {
                throw new IllegalOperationException("Can't substitute restaurant");
            }
        }
    }

    void setDateTime(LocalDateTime date) {
        testingPurposeDate = date;
    }

    private LocalDateTime getCurrentDateTime() {
        if(testingPurposeDate == null) {
            return LocalDateTime.now();
        }
        return testingPurposeDate;
    }

    private boolean beforeToday(LocalDate date) {
        return date.isBefore(getCurrentDateTime().toLocalDate());
    }
}
