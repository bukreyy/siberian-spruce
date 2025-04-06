package com.siberianspruce.account.common.base;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@UtilityClass
public final class EntityUtils {

    public boolean equals(IdentifiableEntity self, Object other) {
        if (self == other) return true;
        if (other == null) return false;
        if (!(other instanceof IdentifiableEntity otherEntity)) return false;
        if (getEffectiveClass(self) != getEffectiveClass(otherEntity)) return false;

        return Objects.equals(self.getId(), otherEntity.getId());
    }

    public int hashCode(IdentifiableEntity entity) {
        return getEffectiveClass(entity).hashCode();
    }

    private static Class<?> getEffectiveClass(Object obj) {
        if (obj instanceof HibernateProxy proxy) {
            return proxy.getHibernateLazyInitializer().getPersistentClass();
        }
        return obj.getClass();
    }

}
