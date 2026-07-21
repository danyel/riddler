package be.riddler.v1;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * TokenTester
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@DisplayName("Token")
class TokenTester {
    @Test
    void name() {
        // Example token generation snippet using your authSecret
        String authSecret = "Mke7DcQEFTfDK1Vi7FVWAlDm3ei5MvEZmLA1nnTfJMM=";

        // 1. Decode base64 to byte array
        byte[] keyBytes = Base64.getDecoder().decode(authSecret);

        // 2. JJWT helper generates the correctly typed SecretKey
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        // 3. Build and sign the JWT safely
        String jwt = Jwts.builder()
                .subject("admin")
                .issuedAt(new Date())
                .claim("roles", List.of("ADMIN", "USER", "X509_AUTHORITY"))
                .expiration(new Date(System.currentTimeMillis() + 99_999_999)) // 1 hour
                .signWith(key) // Automatically applies HS256
                .compact();

        System.out.println("Generated JWT Token:\n" + jwt);
    }
}
