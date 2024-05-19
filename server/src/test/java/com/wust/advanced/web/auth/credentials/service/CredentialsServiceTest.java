package com.wust.advanced.web.auth.credentials.service;

import com.wust.advanced.web.auth.credentials.model.Credentials;
import com.wust.advanced.web.auth.credentials.repository.CredentialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CredentialsServiceTest {

    @Mock
    private CredentialsRepository credentialsRepository;

    @InjectMocks
    private CredentialsService credentialsService;

    private Credentials credentials;

    @BeforeEach
    public void setUp() {
        credentials = Credentials.builder()
                                 .email("test@example.com")
                                 .build();
    }

    @Test
    void loadUserByUsernameReturnsUser() {
        when(credentialsRepository.findByEmail(anyString())).thenReturn(Optional.of(credentials));

        Credentials foundCredentials = credentialsService.loadUserByUsername("test@example.com");

        assertEquals("test@example.com", foundCredentials.getEmail());
        verify(credentialsRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void loadUserByUsernameThrowsUsernameNotFoundException() {
        when(credentialsRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> credentialsService.loadUserByUsername("notfound@example.com"));

        assertEquals(String.format(CredentialsService.USER_WITH_EMAIL_NOT_FOUND, "notfound@example.com"),
                exception.getMessage());
        verify(credentialsRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void createUserDoesNotExistCreatesAndReturnsNewUser() {
        when(credentialsRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(credentialsRepository.save(any(Credentials.class))).thenReturn(Credentials.builder()
                                                                                       .email("new@example.com")
                                                                                       .build());

        Credentials newCredentials = credentialsService.create("new@example.com");

        assertEquals("new@example.com", newCredentials.getEmail());
        verify(credentialsRepository, times(1)).findByEmail(anyString());
        verify(credentialsRepository, times(1)).save(any(Credentials.class));
    }

    @Test
    void createUserExistsReturnsExistingUser() {
        when(credentialsRepository.findByEmail(anyString())).thenReturn(Optional.of(credentials));

        Credentials existingCredentials = credentialsService.create("test@example.com");

        assertEquals("test@example.com", existingCredentials.getEmail());
        verify(credentialsRepository, times(2)).findByEmail(anyString());
        verify(credentialsRepository, times(0)).save(any(Credentials.class));
    }

    @Test
    void areCredentialsExistUserExistsReturnsTrue() {
        when(credentialsRepository.findByEmail(anyString())).thenReturn(Optional.of(credentials));

        assertTrue(credentialsService.areCredentialsExist("test@example.com"));
        verify(credentialsRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void areCredentialsExistUserDoesNotExistReturnsFalse() {
        when(credentialsRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertFalse(credentialsService.areCredentialsExist("notfound@example.com"));
        verify(credentialsRepository, times(1)).findByEmail(anyString());
    }
}