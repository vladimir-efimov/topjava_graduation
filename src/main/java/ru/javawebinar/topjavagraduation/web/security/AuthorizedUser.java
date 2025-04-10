package ru.javawebinar.topjavagraduation.web.security;

import java.util.List;

import ru.javawebinar.topjavagraduation.model.User;


public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getUserRoles(user));
        this.user = user;
    }

    private static List<GrantedAuthorityRole> getUserRoles(User user) {
        return user.getRoles().stream().map(GrantedAuthorityRole::new).toList();
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
