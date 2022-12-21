package io.jumpco.psetbackend.service.impl;

import io.jumpco.psetbackend.mail.EmailSendService;
import io.jumpco.psetbackend.model.Authority;
import io.jumpco.psetbackend.model.ConfirmationToken;
import io.jumpco.psetbackend.model.PsetUser;
import io.jumpco.psetbackend.repository.AuthorityRepository;
import io.jumpco.psetbackend.repository.UserRepository;
import io.jumpco.psetbackend.service.ConfirmationTokenService;
import io.jumpco.psetbackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailSendService emailSendService;

    @Override
    public Page<PsetUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<PsetUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public PsetUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BudgetUser could not be found with id: " + id));
    }

    @Override
    public PsetUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("BudgetUser could not be found with username: " + username));
    }

    @Override
    public PsetUser createUser(PsetUser budgetUser) {
        if(budgetUser.getId() != null){
            throw new IllegalArgumentException("BudgetUser can not already contain an id");
        } else if(budgetUserExists(budgetUser.getUsername())){
            throw new IllegalArgumentException("Email already exists");
        }
        budgetUser.setPassword(passwordEncoder.encode(budgetUser.getPassword()));
        return userRepository.save(budgetUser);
    }

    @Override
    public PsetUser updateUser(PsetUser budgetUser) {
        if(budgetUser.getId() == null){
            throw new IllegalArgumentException("Existing BudgetUser should have an id");
        } else if(!budgetUserExists(budgetUser.getUsername())){
            throw new EntityNotFoundException("Email is not yet registered");
        }
        return userRepository.save(budgetUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean budgetUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public PsetUser activateUser(PsetUser budgetUser) {
        budgetUser.setEnabled(true);
        return userRepository.save(budgetUser);
    }

    @Override
    public PsetUser verifyEmail(PsetUser budgetUser) {
        return activateUser(budgetUser);
    }

    @Override
    public PsetUser registerUser(PsetUser budgetUser) {
        budgetUser.setEnabled(false);
        PsetUser savedUser = createUser(budgetUser);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                savedUser);
        URI uri = URI.create(token);
        StringBuilder builder = new StringBuilder();
        builder.append(
                "Dear " + savedUser.getFirstName() + ".\n" +
                        "\nPlease copy tis code to verify your registration:\n\n" +
                        uri +
                        "\n\nThank you."
        );

        sendEmail(savedUser.getEmail(), "Email verification", builder.toString());
        confirmationToken.setPsetUser(savedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return savedUser;
    }

    @Override
    public Authority updateAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Authority createAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }

    @Override
    public PsetUser confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token could not be found"));
        PsetUser psetUser = confirmationToken.getPsetUser();
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalArgumentException("Token already confirmed");
        }
        if(LocalDateTime.now().isAfter(confirmationToken.getExpiresAt())){
            throw new IllegalArgumentException("Token has already expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        psetUser.setEnabled(true);
        return userRepository.save(psetUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("Users : {}", userRepository.findAll());
        log.error("Username : {}", username);
        PsetUser budgetUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Email not registered yet"));

        return new User(budgetUser.getUsername(),
                budgetUser.getPassword(),
                budgetUser.isEnabled(),
                budgetUser.isAccountNonExpired(),
                budgetUser.isCredentialsNonExpired(),
                budgetUser.isAccountNonLocked(),
                budgetUser.getAuthorities());
    }

    private void sendEmail(String toEmail, String subject, String body) {
        emailSendService.sendEmail(toEmail, subject, body);
    }
}
