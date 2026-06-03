package be.riddler.v1.common.http;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * NotFound
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@Getter
public abstract class NotFound extends RuntimeException {
    private final String title;
    private final String description;
    private final Map<String, Object> properties;

    protected NotFound(String type, UUID id) {
        super("%s with id '%s' not found".formatted(type, id));
        title = "%s Not Found".formatted(type);
        description = getMessage();
        properties = Map.of("id", id);
    }
}
