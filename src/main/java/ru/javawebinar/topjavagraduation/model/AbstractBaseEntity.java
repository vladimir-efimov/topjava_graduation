package ru.javawebinar.topjavagraduation.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

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

    //Method is removed in Hibernate 6
    public static Class getClassWithoutInitializingProxy(Object object) {
        if (object instanceof HibernateProxy) {
            HibernateProxy proxy = (HibernateProxy) object;
            LazyInitializer li = proxy.getHibernateLazyInitializer();
            return li.getPersistentClass();
        }
        else {
            return object.getClass();
        }
    }

    /**
     * Override and throw Exception if entity is not valid
     */
    public void assertValid() {
    }
}
