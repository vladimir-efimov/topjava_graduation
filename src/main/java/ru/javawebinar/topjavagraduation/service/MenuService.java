package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.repository.MenuRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Override
    public Menu create(Menu menu) {
        Menu modifiedMenu = new Menu(null, getCurrentDateTime().toLocalDate(), menu.getRestaurant(), menu.getMeals());
        return super.create(modifiedMenu);
    }

    @Override
    public void update(Menu menu) {
        Menu modifiedMenu = new Menu(menu.getId(), getCurrentDateTime().toLocalDate(), menu.getRestaurant(), menu.getMeals());
        super.update(modifiedMenu);
    }

    public Optional<Menu> findByRestaurantAndDate(int id, Date date) {
        return findByRestaurantAndDate(id, date);
    }

    @Override
    protected void validateOperation(Menu menu, CrudOperation operation) {
        super.validateOperation(menu, operation);
        if(operation == CrudOperation.UPDATE) {
            if(beforeToday(menu.getDate())) {
                throw new IllegalOperationException("Can't operate with menu for the past date");
            }
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
