package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="restaurant", uniqueConstraints = {
        @UniqueConstraint(name="restaurant_unique_name_address_idx", columnNames = {"name", "address"})
})
public class Restaurant extends AbstractManagedEntity {

    @Column(name = "address", nullable = false, length = 128)
    @NotBlank
    @Size(max = 128)
    private String address;

    public Restaurant() {
    }

    public Restaurant(Integer id, boolean enabled, String name, String address) {
        super(id, enabled, name);
        this.address = address;
    }

    public Restaurant(String name, String address) {
        this(null, true, name, address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name=" + name +
                ", address=" + address +
                ", enabled=" + enabled +
                '}';
    }
}
