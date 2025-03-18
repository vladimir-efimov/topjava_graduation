package ru.javawebinar.topjavagraduation.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import static org.slf4j.LoggerFactory.getLogger;

public class LoggingHandlerInterceptor implements HandlerInterceptor {
    private static final Logger log = getLogger(LoggingHandlerInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        log.info("Requested " + request.getRequestURL().toString() +" from " + request.getRemoteHost());
    }
}
