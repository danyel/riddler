package be.riddler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * DatabaseConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Configuration
@EnableJpaAuditing
class DatabaseConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
                return Optional.of("system"); // Fallback for background tasks or public runs
            }

            return Optional.of(authentication.getName()); // Extracts your token principal name
        };
    }
}
