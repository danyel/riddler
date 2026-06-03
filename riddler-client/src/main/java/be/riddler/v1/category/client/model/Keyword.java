package be.riddler.v1.category.client.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Keyword
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
public record Keyword(@NonNull UUID id, @NonNull String word) {
}
