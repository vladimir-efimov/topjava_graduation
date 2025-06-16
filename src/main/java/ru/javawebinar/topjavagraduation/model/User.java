package ru.javawebinar.topjavagraduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "user_email_idx")})
public class User extends AbstractNamedEntity {

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

    @Column(name = "blocked", nullable = false)
    private boolean blocked = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @Schema(hidden = true)
    @JsonIgnore
    private List<Vote> votes;

    public User() {
    }

    public User(Integer id, String name, String email, Set<Role> roles, String password, boolean blocked) {
        super(id, name);
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.blocked = blocked;
    }

    public User(String name, String email, Set<Role> roles, String password) {
        this(null, name, email, roles, password, false);
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

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", roles=" + roles +
                ", blocked=" + blocked +
                '}';
    }
}
