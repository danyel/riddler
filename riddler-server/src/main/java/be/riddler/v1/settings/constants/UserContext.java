package be.riddler.v1.settings.constants;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * UserContext
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@UtilityClass
public class UserContext {

    public static final Stream<String> AVAILABLE_ROLES = Stream.of("ROLE_USER", "ROLE_ADMIN", "ROLE_PARTICIPANT", "ROLE_TEST");

    public static @NonNull List<@NonNull String> roles() {
        List<String> roles = new ArrayList<>();

        if (Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).isAuthenticated()) {
            roles.addAll(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }

        return roles;
    }

    public static @NonNull String username() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }

    public static @NonNull List<@NonNull String> availableRoles() {
        return AVAILABLE_ROLES.filter(e -> roles().contains(e)).toList();
    }
}
