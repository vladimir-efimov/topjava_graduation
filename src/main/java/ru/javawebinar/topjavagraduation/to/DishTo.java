package ru.javawebinar.topjavagraduation.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;


public class DishTo {

    private Integer id;

    @NotBlank
    @Size(max = 64)
    private String name;
    @NotNull
    private Boolean enabled;
    @NotNull
    @Range(min = 0, max = 100000)
    private Float price;
    @NotNull
    private Integer restaurantId;

    public DishTo() {
    }

    public DishTo(Integer id, String name, Boolean enabled, Float price, Integer restaurantId) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
