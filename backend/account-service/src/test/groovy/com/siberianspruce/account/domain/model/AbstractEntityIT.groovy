package com.siberianspruce.account.domain.model

import com.siberianspruce.account.BaseIntegrationTest
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hibernate.proxy.HibernateProxy

abstract class AbstractEntityEqualsSpec<T, ID> extends BaseIntegrationTest {

    @PersistenceContext
    EntityManager em

    abstract Class<T> getEntityClass()

    abstract ID getId()

    ID getDifferentId() {
        switch (getId()) {
            case Long: return 999L as ID
            case String: return "DIFF" as ID
            default:
                throw new UnsupportedOperationException("Provide getDifferentId() for ${getId()?.class?.simpleName}")
        }
    }

    def "should be equal to itself"() {
        when:
        def entity = em.find(getEntityClass(), getId())

        then:
        entity.is(entity)
    }

    def "Should not be equal to null"() {
        when:
        def entity = em.find(getEntityClass(), getId())

        then:
        entity != null
    }

    def "Should not be equal to object of different type"() {
        when:
        def entity = em.find(getEntityClass(), getId())

        then:
        entity != "entity"
    }

    def "Should be equal if same class and same ID"() {
        when:
        def entity1 = em.find(getEntityClass(), getId())
        def entity2 = em.find(getEntityClass(), getId())

        then:
        entity1 == entity2
        entity2 == entity1
    }

    def "Should not be equal if IDs differ"() {
        when:
        def entity = em.find(getEntityClass(), getId())
        def other = getEntityClass()
        other.metaClass.setProperty(other, "id", getDifferentId())

        then:
        entity != other
        other != entity
    }


    def "Should not be equal to object with different effective class"() {
        when:
        def entity = em.find(getEntityClass(), getId())
        def subclass = new UserSubclass(id: 1L)

        then:
        entity != subclass
        subclass != entity
    }

    def "Should be equal to Hibernate proxy with same ID"() {
        when:
        def entity = em.find(getEntityClass(), getId())
        def proxy = em.getReference(getEntityClass(), getId())

        then:
        proxy instanceof HibernateProxy
        entity == proxy
        proxy == entity
    }




    def "should not be equal to null"() {
        when:
        def entity = em.find(getEntityClass(), getExistingId())

        then:
        entity != null
    }

    def "should not be equal to object of different type"() {
        when:
        def entity = em.find(getEntityClass(), getExistingId())

        then:
        entity != "not an entity"
    }

    def "should be equal if same ID and same effective class"() {
        when:
        def entity1 = em.find(getEntityClass(), getExistingId())
        def entity2 = em.find(getEntityClass(), getExistingId())

        then:
        entity1 == entity2
    }

    def "should not be equal if one ID is null"() {
        when:
        def loaded = em.find(getEntityClass(), getExistingId())
        def transientEntity = getEntityClass().newInstance()

        then:
        loaded != transientEntity
        transientEntity != loaded
    }

    def "should not be equal if IDs differ"() {
        when:
        def loaded = em.find(getEntityClass(), getExistingId())
        def other = getEntityClass().newInstance()
        other.metaClass.setProperty(other, "id", 999L)

        then:
        loaded != other
        other != loaded
    }

    def "should not be equal to subclass with same ID"() {
        when:
        def base = em.find(getEntityClass(), getExistingId())
        def subclass = getSubclassInstanceWithSameId()

        then:
        base != subclass
        subclass != base
    }

    def "should have same hashCode for same class"() {
        expect:
        getSameIdInstance().hashCode() == getSameIdInstance().hashCode()
    }

    def "should have different hashCode for subclass"() {
        expect:
        getSameIdInstance().hashCode() != getSubclassInstanceWithSameId().hashCode()
    }

    /**
     * Zwraca podklasę encji z takim samym ID, override jeśli potrzebujesz specjalnej klasy
     */
    T getSubclassInstanceWithSameId() {
        def subclass = new Expando()
        subclass.metaClass.mixin getEntityClass()
        subclass.metaClass.getId = { -> getExistingId() }
        return subclass as T
    }

}
