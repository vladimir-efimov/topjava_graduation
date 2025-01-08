package ru.javawebinar.topjavagraduation.model;

import java.util.EnumSet;
import java.util.Set;

public class User extends AbstractManagedEntity {
    private String email;
    private Set<Role> roles;
    private String password;

    public User() {
    }

    public User(Integer id, boolean enabled, String name, String email, Set<Role> roles, String password) {
        super(id, enabled, name);
        this.email = email;
        this.roles = roles;
        this.password = password;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
