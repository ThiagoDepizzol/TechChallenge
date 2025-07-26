package com.tech.challenge.TechChallenge.service.user;

import com.tech.challenge.TechChallenge.domain.user.User;
import com.tech.challenge.TechChallenge.domain.user.dto.UserLoginDTO;
import com.tech.challenge.TechChallenge.domain.user.dto.UserResetPasswordDTO;
import com.tech.challenge.TechChallenge.repositories.user.UserRepository;
import com.tech.challenge.TechChallenge.service.location.LocationService;
import com.tech.challenge.TechChallenge.utils.exceptions.InvalidCredentialsException;
import com.tech.challenge.TechChallenge.utils.exceptions.InvalidPasswordException;
import com.tech.challenge.TechChallenge.utils.exceptions.UserNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public final UserRepository userRepository;

    public final LocationService locationService;

    public final UserAuthorityService userAuthorityService;

    public LoginService(final UserRepository userRepository, final LocationService locationService, final UserAuthorityService userAuthorityService) {
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.userAuthorityService = userAuthorityService;
    }

    public void resetPassword(@NotNull final UserResetPasswordDTO dto) {

        logger.info("resetPassword -> {}", dto);

        final User user = userRepository.findByEmail(dto.getLogin())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado.")); // Usando UserNotFoundException

        if (!Objects.equals(user.getPassword(), dto.getOldPassword())) {
            throw new InvalidPasswordException("Senha antiga incorreta.");
        }

        user.setPassword(dto.getNewPassword());
        user.setLastModifyDate(Instant.now());

        userRepository.save(user);
    }

    public void login(@NotNull final UserLoginDTO dto) {

        userRepository.getByLogin(dto.getLogin(), dto.getPassword())
                .orElseThrow(() -> new InvalidCredentialsException("Usuário ou senha inválidos"));


    }


}
