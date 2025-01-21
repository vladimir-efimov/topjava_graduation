package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractManagedEntity extends AbstractBaseEntity {

    @Column(name = "enabled", nullable = false)
    protected boolean enabled = true;

    @Column(name = "name", nullable = false, length = 64)
    protected String name;

    protected AbstractManagedEntity() {
    }

    protected AbstractManagedEntity(Integer id, boolean enabled, String name) {
        super(id);
        this.enabled = enabled;
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
