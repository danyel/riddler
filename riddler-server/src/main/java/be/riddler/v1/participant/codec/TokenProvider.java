package be.riddler.v1.participant.codec;

import be.riddler.v1.security.client.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * TokenProvider
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
public class TokenProvider {
    @Value("${application.auth.secret}")
    private String base64Secret;
    private SecretKey verificationKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateToken() {
        byte[] randomBytes = new byte[12];
        secureRandom.nextBytes(randomBytes);

        // Base64Url encoding removes +, / and padding (=) making it perfect for links
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public UserInfo decode(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(verificationKey) // Verifies token signature security integrity
                .build()
                .parseSignedClaims(token)    // Parses the payload elements safely
                .getPayload();

        //noinspection unchecked
        return new UserInfo(claims.getSubject(), (List<String>) claims.get("roles", List.class));
    }

    @PostConstruct
    void postConstruct() {
        byte[] decodedKeyBytes = Base64.getDecoder().decode(base64Secret);
        this.verificationKey = Keys.hmacShaKeyFor(decodedKeyBytes);
    }
}
