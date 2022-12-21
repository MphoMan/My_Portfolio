package io.jumpco.psetbackend.repository;

import io.jumpco.psetbackend.model.PsetUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PsetUser, Long> {
    Optional<PsetUser> findByUsername(String username);
    Optional<PsetUser> findByEmail(String email);
}
