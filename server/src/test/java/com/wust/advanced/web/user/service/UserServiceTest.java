package com.wust.advanced.web.user.service;

import com.wust.advanced.web.auth.credentials.model.Credentials;
import com.wust.advanced.web.auth.credentials.service.CredentialsService;
import com.wust.advanced.web.user.model.FMUser;
import com.wust.advanced.web.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class UserServiceTest {

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Credentials credentials;
    private FMUser fmUser;

    @BeforeEach
     void setUp() {
        credentials = Credentials.builder().email("test@example.com").build();
        fmUser = FMUser.builder()
                       .name("John")
                       .surname("Doe")
                       .credentials(credentials)
                       .build();
    }

    @Test
     void createUserFromAttributesReturnsExistingUser() {
        Map<String, Object> attributes = Map.of(
                "email", "test@example.com",
                "name", "John Doe"
        );

        when(credentialsService.create("test@example.com")).thenReturn(credentials);
        when(userRepository.findByCredentials(credentials)).thenReturn(Optional.of(fmUser));

        FMUser result = userService.createUserFromAttributes(attributes);

        assertEquals(fmUser, result);
        verify(credentialsService, times(1)).create("test@example.com");
        verify(userRepository, times(1)).findByCredentials(credentials);
        verify(userRepository, times(0)).save(any(FMUser.class));
    }

    @Test
     void createUserFromAttributesCreatesAndReturnsNewUser() {
        Map<String, Object> attributes = Map.of(
                "email", "new@example.com",
                "name", "Jane Doe"
        );

        Credentials newCredentials = Credentials.builder().email("new@example.com").build();
        FMUser newUser = FMUser.builder()
                               .name("Jane")
                               .surname("Doe")
                               .credentials(newCredentials)
                               .build();

        when(credentialsService.create("new@example.com")).thenReturn(newCredentials);
        when(userRepository.findByCredentials(newCredentials)).thenReturn(Optional.empty());
        when(userRepository.save(any(FMUser.class))).thenReturn(newUser);

        FMUser result = userService.createUserFromAttributes(attributes);

        assertEquals(newUser, result);
        verify(credentialsService, times(1)).create("new@example.com");
        verify(userRepository, times(1)).findByCredentials(newCredentials);
        verify(userRepository, times(1)).save(any(FMUser.class));
    }
}