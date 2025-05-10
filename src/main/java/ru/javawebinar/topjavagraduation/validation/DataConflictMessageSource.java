package ru.javawebinar.topjavagraduation.validation;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.Map;

public class DataConflictMessageSource {

    private static final Map<String, String> messages = Map.of(
            "dish_unique_name_restaurant_idx", "Dish with the same name and restaurant already exists",
            "menu_unique_date_restaurant_idx", "Menu for the same date and restaurant already exists",
            "restaurant_unique_name_address_idx", "Restaurant with the same name and address already exists",
            "user_email_idx", "Email is already used for another user"
    );

    public static String getMessage(DataIntegrityViolationException e) {
        String constraint = extractConstraint(e.getMessage());
        return messages.getOrDefault(constraint, "");
    }

    private static String extractConstraint(String errorMessage) {
        return errorMessage
                .replaceFirst(".*constraint \\[", "")
                .replaceFirst("]$", "")
                .toLowerCase();
    }
}
