package ru.javawebinar.topjavagraduation.web.security;

import org.springframework.security.core.GrantedAuthority;
import ru.javawebinar.topjavagraduation.model.Role;


public class GrantedAuthorityRole implements GrantedAuthority {

    private final Role role;

    public GrantedAuthorityRole(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }
}
