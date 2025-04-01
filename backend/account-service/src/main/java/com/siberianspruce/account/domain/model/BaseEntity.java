package com.siberianspruce.account.domain.model;

public abstract class BaseEntity {

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> thisClass = getEffectiveClass(this);
        Class<?> otherClass = getEffectiveClass(o);

        if (!thisClass.equals(otherClass)) return false;

        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public final int hashCode() {
        return getEffectiveClass(this).hashCode();
    }

    private static Class<?> getEffectiveClass(Object obj) {
        if (obj instanceof HibernateProxy proxy) {
            return proxy.getHibernateLazyInitializer().getPersistentClass();
        }
        return obj.getClass();
    }

}
