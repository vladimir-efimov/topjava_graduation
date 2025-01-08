package ru.javawebinar.topjavagraduation.model;

public abstract class AbstractBaseEntity implements Cloneable {

    protected Integer id;

    protected AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public AbstractBaseEntity clone() throws CloneNotSupportedException {
        return (AbstractBaseEntity) super.clone();
    }
}
