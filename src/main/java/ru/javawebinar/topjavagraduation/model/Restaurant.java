package ru.javawebinar.topjavagraduation.model;

public class Restaurant extends AbstractBaseEntity {
    private String name;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
