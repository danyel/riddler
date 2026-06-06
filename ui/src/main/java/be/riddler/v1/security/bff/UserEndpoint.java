package be.riddler.v1.security.bff;

import be.riddler.v1.common.http.Unauthorized;
import be.riddler.v1.invitation.repository.InvitationRepository;
import be.riddler.v1.security.client.model.UserInfo;
import be.riddler.v1.settings.constants.UserContext;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * UserService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = PACKAGE)
public class UserEndpoint {
    private final InvitationRepository invitationRepository;

    public @NonNull UserInfo getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Assert.isTrue(auth != null, "Cannot pass null");
        return new UserInfo(
                auth.getName(),
                auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    public void useToken(UUID invitationId, String token) {
        var invitationEntity = invitationRepository.findById(invitationId).orElseThrow();
        if (Objects.equals(invitationEntity.getStoredToken(), token)) {
            SecurityContextHolder.clearContext();
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_PARTICIPANT"));
            var authentication = new UsernamePasswordAuthenticationToken(invitationEntity.getParticipantId(), token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
            throw new Unauthorized("Invalid token");
        }
    }

    public @NonNull List<@NonNull String> getAuthorities() {
        return UserContext.availableRoles();
    }
}
