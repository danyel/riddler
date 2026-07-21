package be.riddler.v1.security.bff;

import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.security.client.SecurityClient;
import be.riddler.v1.security.client.model.UserInfo;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
    private final InvitationClient invitationClient;
    private final SecurityClient securityClient;

    public @NonNull UserInfo getAuthenticatedUser() {
        return securityClient.getAuthenticatedUser();
    }

    public void useToken(UUID invitationId, String token) {
        var invitation = invitationClient.findById(invitationId);
        if (Objects.equals(invitation.storedToken(), token)) {
            SecurityContextHolder.clearContext();
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_PARTICIPANT"));
            var authentication = new UsernamePasswordAuthenticationToken(invitation.participantId(), token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }
    }

    public @NonNull List<@NonNull String> getAuthorities() {
        return securityClient.getAuthorities();
    }
}
