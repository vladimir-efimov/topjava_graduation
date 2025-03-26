package ru.javawebinar.topjavagraduation.web.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {

    public static Optional<Integer> getAuthorizedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            return Optional.empty();
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ?
                Optional.of(((AuthorizedUser) principal).getId())
                : Optional.empty();
    }

}
