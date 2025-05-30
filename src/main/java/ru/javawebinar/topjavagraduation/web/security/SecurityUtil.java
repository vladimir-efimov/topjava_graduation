package ru.javawebinar.topjavagraduation.web.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;


public class SecurityUtil {

    public static Optional<Integer> safeGetAuthorizedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Optional.empty();
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ?
                Optional.of(((AuthorizedUser) principal).getId())
                : Optional.empty();
    }

    public static int getAuthorizedUserId() {
        var optional = safeGetAuthorizedUserId();
        if (optional.isEmpty()) {
            throw new IllegalOperationException("Operation is forbidden for not authorized user");
        }
        return optional.get();
    }
}
