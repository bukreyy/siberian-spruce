package com.siberianspruce.account

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [Initializer])
abstract class BaseIntegrationTest extends Specification {

    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("test-account-db")
            .withUsername("test")
            .withPassword("test")

    static {
        POSTGRES.start()
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=${POSTGRES.jdbcUrl}",
                    "spring.datasource.username=${POSTGRES.username}",
                    "spring.datasource.password=${POSTGRES.password}",
                    "spring.datasource.driver-class-name=org.postgresql.Driver"
            ).applyTo(context.environment)
        }
    }

}
