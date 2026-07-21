package be.riddler.configuration.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JwtAuthenticationFilter
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String JWT_HEADER_AND_PAYLOAD = "jwt.headerAndPayload";
    public static final String JWT_SIGNATURE = "jwt.signature";
    private final SecretKey secretKey;

    public JwtAuthenticationFilter(String base64Secret) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws IOException, ServletException {
        var token = token(request);
        buildSecurityContext(token);
        filterChain.doFilter(request, response);
    }

    private static String token(@NonNull HttpServletRequest request) {
        var cookies = getCookies(request);
        if (cookies.containsKey(JWT_HEADER_AND_PAYLOAD) && cookies.containsKey(JWT_SIGNATURE)) {
            return cookies.get(JWT_HEADER_AND_PAYLOAD) + "." + cookies.get(JWT_SIGNATURE);
        }

        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private void buildSecurityContext(String token) {
        if (token == null) return;
        try {
            var claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            var username = claims.getSubject();

            @SuppressWarnings("unchecked")
            var roles = (List<String>) claims.get("roles", List.class);
            var authorities = mapAuthorities(roles);

            var authentication = new UsernamePasswordAuthenticationToken(username, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("JWT authentication token: {} and authenticated: {}", token, authentication);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }

    private static @NonNull Map<@NonNull String, @NonNull String> getCookies(@NonNull HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> List.of(JWT_SIGNATURE, JWT_HEADER_AND_PAYLOAD).contains(cookie.getName()))
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        }
        return Map.of();
    }

    private static @NonNull List<SimpleGrantedAuthority> mapAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> role.startsWith("ROLE_") || "X509_AUTHORITY".equals(role) ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
