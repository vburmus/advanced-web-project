package com.wust.advanced.web.auth.oauth;

import com.wust.advanced.web.user.model.FMUser;
import com.wust.advanced.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        FMUser user = userService.createUserFromAttributes(attributes);
        Set<GrantedAuthority> authoritySet = new HashSet<>(oidcUser.getAuthorities());
        authoritySet.add(new SimpleGrantedAuthority(user.getCredentials().getRole().name()));
        OidcIdToken token = userRequest.getIdToken();
        return new DefaultOidcUser(authoritySet, token, "sub");
    }
}