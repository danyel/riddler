package be.riddler.v1.security.api;

import be.riddler.v1.security.client.SecurityClient;
import be.riddler.v1.security.client.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Objects;

import static lombok.AccessLevel.PACKAGE;

/**
 * SecurityResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@HttpExchange(url = "/v1/security")
@RequiredArgsConstructor(access = PACKAGE)
class SecurityResource implements SecurityClient {

    @Override
    public UserInfo authenticate(String username, String password) {
        var userDetails = new UsernamePasswordAuthenticationToken(username, password);
        return new UserInfo(userDetails.getName(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(Objects::nonNull).toList());
    }
}
