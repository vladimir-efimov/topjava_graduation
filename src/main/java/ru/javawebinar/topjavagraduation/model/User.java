package ru.javawebinar.topjavagraduation.model;

import java.util.Set;

public class User extends AbstractBaseEntity {
    private String name;
    private String email;
    private Set<Role> roles;
    private String password;

    public User() {
    }

    public User(Integer id, String name, String email, Set<Role> roles, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
