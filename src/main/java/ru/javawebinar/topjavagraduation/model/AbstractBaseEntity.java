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

    /* todo: consider using business-model comparison instead of DAO-style comparison based on id.
       The problem with id-based comparison is that
       - in-memory repository can't utilize hash code for preventing unique constraint violations
       - tests can't rely on equals(), tests will use matchers
       Need to check how equals and hashCode is used by ORM. After that following approach may be
       tested:
       - hashCode() is be defined on fields composing unique constraint
       - equals() compares fields bypassing comparison of null id with not-null id
     */

    //todo: substitution getClass() when plug-in ORM, need take into account proxy classes and lazy initialization
    //https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id == ((AbstractBaseEntity) o).getId();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
