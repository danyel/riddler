package be.riddler.v1.security.api;

import be.riddler.v1.security.client.SecurityClient;
import be.riddler.v1.security.client.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static lombok.AccessLevel.PACKAGE;

/**
 * SecurityResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequestMapping(path = "/v1/security")
@RequiredArgsConstructor(access = PACKAGE)
class SecurityResource implements SecurityClient {
    private final UserDetailsService userDetailsService;

    @Override
    public UserInfo authenticate(String username, String password) {
        var userDetails = userDetailsService.loadUserByUsername(username);
        return new UserInfo(userDetails.getUsername(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(Objects::nonNull).toList());
    }
}
