package ru.javawebinar.topjavagraduation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import ru.javawebinar.topjavagraduation.model.Role;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/rest/**").authorizeHttpRequests(authz ->
                        authz.requestMatchers("/rest/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/rest/profile").anonymous()
                                .requestMatchers("/rest/**").authenticated()
                                .requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll())
                .httpBasic(withDefaults())
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
