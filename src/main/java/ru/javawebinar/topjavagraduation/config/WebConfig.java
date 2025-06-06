package ru.javawebinar.topjavagraduation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.javawebinar.topjavagraduation.topjava.JacksonObjectMapper;
import ru.javawebinar.topjavagraduation.web.interceptors.LoggingHandlerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LoggingHandlerInterceptor loggingInterceptor;

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        var messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(JacksonObjectMapper.getMapper());
        return messageConverter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }
}
