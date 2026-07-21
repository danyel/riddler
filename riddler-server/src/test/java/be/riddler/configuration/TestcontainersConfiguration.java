package be.riddler.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/// TestcontainersConfiguration
///
/// @author dnoulet
/// @version 1.0.0 11/04/2026
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {
    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        //noinspection resource
        return new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
                .withDatabaseName("riddler") // Sets the database name used in the JDBC URL
                .withUsername("app_user")
                .withPassword("app_user_password");
    }
}
