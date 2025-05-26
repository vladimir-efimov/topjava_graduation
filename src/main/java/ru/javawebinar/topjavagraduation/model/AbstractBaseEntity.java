package ru.javawebinar.topjavagraduation.model;

import javax.persistence.*;
import static org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClassWithoutInitializingProxy(this) != getClassWithoutInitializingProxy(o)) return false;
        return getId() != null && getId().equals(((AbstractBaseEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClassWithoutInitializingProxy(this).hashCode();
    }

    /**
     * Override and throw Exception if entity is not valid
     */
    public void assertValid() {
    }
}
