package com.tech.challenge.TechChallenge.service.user;

import com.tech.challenge.TechChallenge.domain.location.Location;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public final UserRepository userRepository;

    public final LocationService locationService;

    public UserService(final UserRepository userRepository, final LocationService locationService) {
        this.userRepository = userRepository;
        this.locationService = locationService;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll -> {}", pageable);

        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(@NotNull final Long id) {

        logger.info("findById -> {}", id);

        return userRepository.findById(id);
    }

    public User created(@NotNull final User user) {

        logger.info("created -> {}", user);

        final Location location = Optional.ofNullable(user.getLocation())
                .filter(loc -> loc.getId() != null)
                .flatMap(loc -> locationService.findById(loc.getId()))
                .orElseGet(() -> locationService.created(user.getLocation()));

        user.setLocation(location);
        user.setLastModifyDate(Instant.now());

        return userRepository.save(user);

    }

    public User update(@NotNull final User user, @NotNull final Long id) {

        logger.info("update -> {}", user);

        return userRepository.findById(id)
                .map(User::loadLocation)
                .map(bdUser -> {

                    user.setId(bdUser.getId());
                    user.setLastModifyDate(Instant.now());

                    return this.userRepository.save(user);

                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public void delete(@NotNull final Long id) {

        logger.info("delete -> {}", id);

        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setActive(false);

                    userRepository.save(user);
                });
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
