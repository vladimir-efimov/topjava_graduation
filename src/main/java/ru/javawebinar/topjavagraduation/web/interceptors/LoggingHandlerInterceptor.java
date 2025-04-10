package ru.javawebinar.topjavagraduation.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjavagraduation.web.security.SecurityUtil.safeGetAuthorizedUserId;


public class LoggingHandlerInterceptor implements HandlerInterceptor {
    private static final Logger log = getLogger(LoggingHandlerInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        var optional = safeGetAuthorizedUserId();
        String userString = optional.isPresent() ? "user with id=" + optional.get() : "anonymous user";
        log.info("Requested " + request.getRequestURL().toString() + " from " + request.getRemoteHost() + " by " + userString);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        int status = response.getStatus();
        if (status != HttpStatus.OK.value() && status != HttpStatus.NO_CONTENT.value()) {
            var optional = safeGetAuthorizedUserId();
            String userString = optional.isPresent() ? "user with id=" + optional.get() : "anonymous user";
            log.info("Request " + request.getRequestURL().toString() + " from " + request.getRemoteHost() +
                    " by " + userString + " finished with status " + status);
        }
    }
}
