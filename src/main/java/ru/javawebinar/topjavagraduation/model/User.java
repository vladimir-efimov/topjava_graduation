package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.EnumSet;
import java.util.Set;


@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "user_email_idx")})
public class User extends AbstractManagedEntity {

    @Column(name = "email", nullable = false, length = 64)
    @Email
    @Size(max = 64)
    private String email;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = EnumSet.noneOf(Role.class);

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 64)
    private String password;

    public User() {
    }

    public User(Integer id, boolean enabled, String name, String email, Set<Role> roles, String password) {
        super(id, enabled, name);
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public User(String name, String email, Set<Role> roles, String password) {
        this(null, true, name, email, roles, password);
    }

    public User(String name, String email, Role role, String password) {
        this(name, email, Set.of(role), password);
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
