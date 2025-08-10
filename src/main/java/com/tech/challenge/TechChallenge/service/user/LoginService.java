package com.tech.challenge.TechChallenge.service.user;

import com.tech.challenge.TechChallenge.domain.user.User;
import com.tech.challenge.TechChallenge.domain.user.dto.UserResetPasswordDTO;
import com.tech.challenge.TechChallenge.repositories.user.UserRepository;
import com.tech.challenge.TechChallenge.service.location.LocationService;
import com.tech.challenge.TechChallenge.utils.exceptions.InvalidPasswordException;
import com.tech.challenge.TechChallenge.utils.exceptions.UserNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public final UserRepository userRepository;

    public final LocationService locationService;

    public final UserAuthorityService userAuthorityService;

    private final PasswordEncoder passwordEncoder;

    public LoginService(final UserRepository userRepository, final LocationService locationService, final UserAuthorityService userAuthorityService, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.userAuthorityService = userAuthorityService;
        this.passwordEncoder = passwordEncoder;
    }

    public void resetPassword(@NotNull final UserResetPasswordDTO dto) {

        logger.info("resetPassword -> {}", dto);

        final User user = userRepository.findByEmail(dto.getLogin())
                .orElseThrow(() -> {

                    logger.info("Email incorreto");

                    return new UserNotFoundException("Usuário ou senha inválidos");

                });

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {

            logger.info("Senha antiga incorreta");

            throw new InvalidPasswordException("Usuário ou senha inválidos");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setLastModifyDate(Instant.now());

        userRepository.save(user);
    }

}
