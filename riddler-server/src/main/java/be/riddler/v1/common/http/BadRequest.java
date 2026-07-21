package be.riddler.v1.common.http;

import lombok.Getter;
import org.jspecify.annotations.NonNull;

/**
 * BadRequest
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@Getter
public class BadRequest extends RuntimeException {
    private final Object original;

    public BadRequest(@NonNull Object original, String message) {
        super(message);
        this.original = original;
    }
}
