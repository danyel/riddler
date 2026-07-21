package be.riddler.v1.common.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Unauthorized
 *
 * @author dnoulet
 * @version 1.0.0 01/05/2026
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }
}
