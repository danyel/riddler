package be.riddler.configuration;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import com.vaadin.flow.spring.security.stateless.VaadinStatelessSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


/**
 * SecurityConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
class SecurityConfiguration {
    @Value("${application.auth.secret}")
    private String authSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        // 1. Establish absolute statelessness
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 2. DISABLE CSRF for REST APIs so the Authorization header is processed
        RequestMatcher apiMatcher = PathPatternRequestMatcher.withDefaults().matcher("/v1/**");
        http.csrf(csrf -> csrf.ignoringRequestMatchers(apiMatcher));

        // 3. Define public matching rules FIRST
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(
                    "/line-awesome/**", "/themes/**", "/VAADIN/**",
                    "/swagger-ui/**", "/v3/api-docs/**", "/connect/**", "/v1/**"
            ).permitAll();
            auth.requestMatchers(
                    "/question/**", "/questions/**", "/icons/**"
            ).permitAll();
            auth.requestMatchers(
                    "/", "/login"
            ).permitAll();
        });

        // 4. Return clean 401 Unauthorized for REST paths instead of 302 Redirect
        http.exceptionHandling(exceptions -> exceptions
                .defaultAuthenticationEntryPointFor(
                        (_, response, _) -> response.sendError(401, "Unauthorized"),
                        apiMatcher
                )
        );

        // 6. Apply Vaadin security
        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> configurer.loginView("/login"));

        // 7. Apply stateless configurer
        http.with(new VaadinStatelessSecurityConfigurer<>(), cfg -> cfg.withSecretKey()
                .secretKey(new SecretKeySpec(
                        Base64.getDecoder().decode(authSecret),
                        JwsAlgorithms.HS256
                )).and().issuer("be.riddler")
        );

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .authorities("ROLE_ADMIN", "ROLE_USER", "ROLE_PARTICIPANT")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}user")
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}

@Controller
class SpaForwardingController {
    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "{path:[^.]*}")
    String forward() {
        return "forward:/";
    }
}