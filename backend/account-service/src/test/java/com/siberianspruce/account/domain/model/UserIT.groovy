package com.siberianspruce.account.domain.model

import com.siberianspruce.account.BaseIntegrationTest
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hibernate.proxy.HibernateProxy
import org.springframework.test.context.jdbc.Sql

@Sql(scripts = ["/sql/clear.sql", "/sql/domain/model/user.sql"])
class UserIT extends BaseIntegrationTest {

    @PersistenceContext
    EntityManager em

    def "should be equal to itself"() {
        when:
        def user = em.find(User, 100L)

        then:
        user == user
    }

    def "should not be equal to null"() {
        when:
        def user = em.find(User, 100L)

        then:
        user != null
    }

    def "should not be equal to object of different type"() {
        when:
        def user = em.find(User, 100L)

        then:
        user != "not a user"
    }

    def "should not be equal if IDs differ"() {
        given:
        def other = new User(id: 200L)

        when:
        def user = em.find(User, 100L)

        then:
        user != other
        other != user
    }

    def "should not be equal if one ID is null"() {
        given:
        def transientUser = new User(email: "john@example.com", password: "pass123")

        when:
        def user = em.find(User, 100L)

        then:
        user != transientUser
        transientUser != user
    }

    def "should be equal to its Hibernate proxy"() {
        when:
        def user = em.find(User, 100L)
        def proxy = em.getReference(User, 100L)

        then:
        proxy instanceof HibernateProxy
        user == proxy
        proxy == user
    }

    def "should not be equal to subclass with same ID"() {
        given:
        def subclass = new UserSubclass(id: 100L)

        when:
        def user = em.find(User, 100L)

        then:
        user != subclass
        subclass != user
    }

    def "should have same hashCode for same class"() {
        expect:
        new User().hashCode() == new User().hashCode()
    }

    def "should have different hashCode for different effective class"() {
        expect:
        new User().hashCode() != new UserSubclass().hashCode()
    }

    static class UserSubclass extends User {}

}
