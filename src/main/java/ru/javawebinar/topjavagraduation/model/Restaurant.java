package ru.javawebinar.topjavagraduation.model;

public class Restaurant extends AbstractManagedEntity {

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
