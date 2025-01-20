package ru.javawebinar.topjavagraduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjavagraduation.model.Menu;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.InMemoryMenuRepository;
import ru.javawebinar.topjavagraduation.repository.InMemoryRestaurantRepository;
import ru.javawebinar.topjavagraduation.repository.MenuRepository;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;

import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceTest {

    RestaurantRepository restaurantRepository;
    MenuRepository menuRepository;
    MenuService service;

    @BeforeEach
    void setup() {
        restaurantRepository = new InMemoryRestaurantRepository();
        menuRepository = new InMemoryMenuRepository();
        service = new MenuService(menuRepository);
        for (Restaurant restaurant : TestData.restaurants) {
            restaurantRepository.save(new Restaurant(restaurant.getName(), restaurant.getAddress()));
        }
        for (Menu menu : TestData.menus) {
            menuRepository.save(new Menu(null, menu.getDate(), menu.getRestaurant(), menu.getMeals()));
        }
    }

    @Test
    void create() {
        Menu savedMenu = service.create(TestData.newMenu);
        assertNotNull(savedMenu.getId());
        assertEquals(TestData.newMenu.getRestaurant(), savedMenu.getRestaurant());
    }

    @Test
    void tryCreateInvalid() {
        for (Menu menu : TestData.invalidMenus) {
            assertThrows(IllegalArgumentException.class, () -> service.create(menu));
        }
    }

    @Test
    @Disabled
    void tryUpdateInvalid() {
        assertThrows(IllegalOperationException.class, () -> service.update(TestData.invalidUpdateMenu));
    }
}