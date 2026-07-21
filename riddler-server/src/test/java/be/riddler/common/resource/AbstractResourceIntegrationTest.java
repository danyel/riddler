package be.riddler.common.resource;

import be.riddler.common.AbstractContainerTest;
import be.riddler.configuration.TestcontainersConfiguration;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static be.riddler.common.header.Headers.X_CORRELATION_ID;
import static be.riddler.configuration.filter.JwtAuthenticationFilter.JWT_HEADER_AND_PAYLOAD;
import static be.riddler.configuration.filter.JwtAuthenticationFilter.JWT_SIGNATURE;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.time.Instant.now;
import static java.util.Base64.getDecoder;
import static java.util.Date.from;
import static java.util.List.of;
import static java.util.UUID.randomUUID;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * AbstractResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
@Import({TestcontainersConfiguration.class})
public abstract class AbstractResourceIntegrationTest extends AbstractContainerTest {
    @Autowired
    @Getter
    private WebTestClient webTestClient;

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${application.auth.secret}")
    private String authSecret;
    private final Map<String, String> cookies = new HashMap<>();

    @BeforeEach
    void setUp() {
        webTestClient.mutate()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
        var secretKey = hmacShaKeyFor(getDecoder().decode(authSecret));
        var oneHourInSeconds = 3600;
        var fullToken = builder()
                .subject("user")
                .claim("roles", of("USER", "X509_AUTHORITY"))
                .expiration(from(now().plusSeconds(oneHourInSeconds)))
                .signWith(secretKey)
                .compact();
        var lastPeriodIndex = fullToken.lastIndexOf('.');
        var headerAndPayload = fullToken.substring(0, lastPeriodIndex);
        var signature = fullToken.substring(lastPeriodIndex + 1);

        cookies.put(JWT_HEADER_AND_PAYLOAD, headerAndPayload);
        cookies.put(JWT_SIGNATURE, signature);
    }

    protected WebTestClient.ResponseSpec doExchange(WebTestClient.RequestHeadersSpec<?> uriSpec) {
        return uriSpec.accept(APPLICATION_JSON)
                .header(X_CORRELATION_ID, correlationId())
                .accept(APPLICATION_JSON)
                .cookie(JWT_SIGNATURE, jwtSignature())
                .cookie(JWT_HEADER_AND_PAYLOAD, jwtHeaderAndPayload())
                .exchange();
    }

    protected String jwtSignature() {
        return cookies.get(JWT_SIGNATURE);
    }

    protected String jwtHeaderAndPayload() {
        return cookies.get(JWT_HEADER_AND_PAYLOAD);
    }

    protected String correlationId() {
        return newId().toString();
    }

    protected UUID newId() {
        return randomUUID();
    }

    protected String authenticationContent() {
        var authentication = new UsernamePasswordAuthenticationToken("username", "password", of((GrantedAuthority) () -> "AMDIN"));
        var authenticationBytes = objectMapper.writeValueAsBytes(authentication);
        return Base64.getEncoder().encodeToString(authenticationBytes);
    }
}
