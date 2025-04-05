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
        def user = em.find(User, 1L)

        then:
        user == user
    }

    def "should not be equal to null"() {
        when:
        def user = em.find(User, 1L)

        then:
        user != null
    }


}
