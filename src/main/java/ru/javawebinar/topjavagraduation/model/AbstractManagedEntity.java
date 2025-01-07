package ru.javawebinar.topjavagraduation.model;

public class AbstractManagedEntity extends AbstractBaseEntity {

    protected boolean enabled = true;

    protected AbstractManagedEntity() {
    }

    protected AbstractManagedEntity(Integer id, boolean enabled) {
        super(id);
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
