package com.wust.advanced.web.user.repository;

import com.wust.advanced.web.auth.credentials.model.Credentials;
import com.wust.advanced.web.user.model.FMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<FMUser,Long> {
    Optional<FMUser> findByCredentials(Credentials credentials);
    Optional<FMUser> findByCredentials_Email(String email);
}