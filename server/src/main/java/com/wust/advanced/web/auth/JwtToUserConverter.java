package com.wust.advanced.web.auth;

import com.wust.advanced.web.auth.credentials.model.Credentials;
import com.wust.advanced.web.auth.credentials.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        Credentials credentials = Credentials.builder()
                               .role(Role.valueOf(jwt.getClaim("role")))
                               .email(jwt.getSubject())
                               .build();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(credentials.getRole().name()));
        return new UsernamePasswordAuthenticationToken(credentials, jwt, authorities);
    }
}