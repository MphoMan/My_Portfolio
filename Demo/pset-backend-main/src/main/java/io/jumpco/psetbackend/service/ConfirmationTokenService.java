package io.jumpco.psetbackend.service;

import io.jumpco.psetbackend.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> findByToken(String token);
}
