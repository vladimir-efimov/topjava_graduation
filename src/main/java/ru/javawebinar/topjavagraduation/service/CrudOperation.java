package ru.javawebinar.topjavagraduation.service;

public enum CrudOperation {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private final String name;

    CrudOperation(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
