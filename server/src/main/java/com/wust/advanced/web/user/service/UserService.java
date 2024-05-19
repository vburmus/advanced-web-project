package com.wust.advanced.web.user.service;

import com.wust.advanced.web.auth.credentials.model.Credentials;
import com.wust.advanced.web.auth.credentials.service.CredentialsService;
import com.wust.advanced.web.user.model.FMUser;
import com.wust.advanced.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CredentialsService credentialsService;
    private final UserRepository userRepository;

    public FMUser createUserFromAttributes(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        Credentials credentials = credentialsService.create(email);
        Optional<FMUser> user = userRepository.findByCredentials(credentials);
        if(user.isPresent()){
            return user.get();
        }
        String[] fullName = attributes.get("name").toString().split(" ");
        FMUser newUser = FMUser.builder()
                               .name(fullName[0])
                               .surname(fullName[1])
                               .credentials(credentials)
                               .build();

        return userRepository.save(newUser);
    }
}
