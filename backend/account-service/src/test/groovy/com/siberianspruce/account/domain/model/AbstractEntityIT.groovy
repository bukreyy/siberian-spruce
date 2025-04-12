package com.siberianspruce.account.domain.model

import com.siberianspruce.account.BaseIntegrationTest
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hibernate.proxy.HibernateProxy

abstract class AbstractEntityEqualsSpec<T, ID> extends BaseIntegrationTest {

    @PersistenceContext
    EntityManager em

    abstract Class<T> getEntityClass()

    abstract Serializable getId()

    def "Should be equal to itself"() {
        when:
        def entity = em.find(getEntityClass(), getId())

        then:
        entity == entity
    }

    def "Should be equal to proxy"() {
        when:
        def entity = em.find(getEntityClass(), getId())
        def proxy = em.getReference(getEntityClass(), getId())

        then:
        entity == proxy
        proxy == entity
    }

    def "Should have same hashCode as proxy"() {
        when:
        def entity = em.find(getEntityClass(), getId())
        def proxy = em.getReference(getEntityClass(), getId())

        then:
        entity.hashCode() == proxy.hashCode()
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
        entity != new Object()
    }

    def "Should not be equal to same type with different id"() {
        given:
        def entity = em.find(getEntityClass(), getId())
        def other = em.find(getEntityClass(), getDifferentId())

        expect:
        entity != other
    }

    def "Should not be equal to different type with same id"() {
        given:
        def entity = em.find(getEntityClass(), getId())
        def other = em.find(getOtherEntityClass(), getId())

        expect:
        entity != other
    }


}
