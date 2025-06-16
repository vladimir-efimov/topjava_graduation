package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@MappedSuperclass
public class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(max = 64)
    @Column(name = "name", nullable = false, length = 64)
    protected String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
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
