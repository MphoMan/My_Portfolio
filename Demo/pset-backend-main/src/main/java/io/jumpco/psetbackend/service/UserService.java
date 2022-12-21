package io.jumpco.psetbackend.service;

import io.jumpco.psetbackend.model.Authority;
import io.jumpco.psetbackend.model.PsetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<PsetUser> findAll(Pageable pageable);
    List<PsetUser> findAll();
    PsetUser findById(Long id);
    PsetUser findByUsername(String username);
    PsetUser createUser(PsetUser budgetUser);
    PsetUser updateUser(PsetUser budgetUser);
    void deleteUser(Long id);
    boolean budgetUserExists(String username);
    PsetUser activateUser(PsetUser budgetUser);
    PsetUser verifyEmail(PsetUser budgetUser);
    PsetUser registerUser(PsetUser budgetUser);
    Authority updateAuthority(Authority authority);
    Authority createAuthority(Authority authority);

    Authority findByName(String name);

    PsetUser confirmEmail(String token);
}
