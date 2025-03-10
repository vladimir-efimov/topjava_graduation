package ru.javawebinar.topjavagraduation.validation;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.Map;

public class DataConflictMessageSource {

    private static final Map<String, String> messages = Map.of(
            "UK_EMAIL_IDX", "Email is already used for another user"
    );

    public static String getMessage(DataIntegrityViolationException e) {
        String constraint = extractConstraint(e.getMessage());
        return messages.getOrDefault(constraint, "");
    }

    private static String extractConstraint(String errorMessage) {
        return errorMessage
                .replaceFirst(".*constraint \\[", "")
                .replaceFirst("]$", "");
    }
}
