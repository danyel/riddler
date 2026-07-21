package be.riddler.v1.security.api;

import be.riddler.v1.security.client.SecurityClient;
import be.riddler.v1.security.client.model.UserInfo;
import be.riddler.v1.settings.constants.UserContext;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Objects;

import static be.riddler.common.path.Paths.SECURITY;
import static lombok.AccessLevel.PACKAGE;

/**
 * SecurityResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@HttpExchange(url = SECURITY)
@RequiredArgsConstructor(access = PACKAGE)
class SecurityResource implements SecurityClient {

    @Override
    public UserInfo authenticate(String username, String password) {
        var userDetails = new UsernamePasswordAuthenticationToken(username, password);
        return new UserInfo(userDetails.getName(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(Objects::nonNull).toList());
    }

    @Override
    public @NonNull List<@NonNull String> getAuthorities() {
        return UserContext.availableRoles();
    }

    @Override
    public UserInfo getAuthenticatedUser() {
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
}
