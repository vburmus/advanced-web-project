package com.wust.advanced.web.auth.credentials.service;

import com.wust.advanced.web.auth.credentials.model.Credentials;
import com.wust.advanced.web.auth.credentials.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialsService implements UserDetailsService {
    public static final String USER_WITH_EMAIL_NOT_FOUND = "User with email %s not found";
    private final CredentialsRepository credentialsRepository;

    @Override
    public Credentials loadUserByUsername(String email) {
        return credentialsRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(
                        USER_WITH_EMAIL_NOT_FOUND, email)));
    }

    public Credentials create(String email) {
        if (areCredentialsExist(email)) {
            return loadUserByUsername(email);
        }
        Credentials newCredentials = Credentials.builder()
                                                .email(email)
                                                .build();
        return credentialsRepository.save(newCredentials);
    }

    public boolean areCredentialsExist(String email) {
        return credentialsRepository.findByEmail(email).isPresent();
    }
}