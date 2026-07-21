package be.riddler.configuration;

import be.riddler.common.path.Paths;
import be.riddler.configuration.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * SecurityConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
class ServerSecurityConfiguration {
    @Value("${application.auth.secret}")
    private String authSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        var apiMatcher = new RegexRequestMatcher(".*(/v1/).*", null);
        http.csrf(csrf -> csrf.ignoringRequestMatchers(apiMatcher));
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(
                    "/swagger-ui/**", "/v3/api-docs/**", "%s/**".formatted(Paths.SECURITY)
            ).permitAll();
            auth.requestMatchers(apiMatcher).authenticated();

            auth.anyRequest().permitAll();
        });
        http.exceptionHandling(exceptions -> exceptions
                .defaultAuthenticationEntryPointFor(
                        (_, response, _) -> response.sendError(401, "Unauthorized"),
                        apiMatcher
                )
        );
        http.addFilterBefore(new JwtAuthenticationFilter(authSecret), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .authorities("ROLE_ADMIN", "ROLE_USER", "ROLE_PARTICIPANT")
                .build();

        UserDetails user = User.withUsername("dnoulet")
                .password("{noop}dnoulet")
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}