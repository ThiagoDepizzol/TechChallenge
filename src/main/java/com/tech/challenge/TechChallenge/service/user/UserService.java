package com.tech.challenge.TechChallenge.service.user;

import com.tech.challenge.TechChallenge.domain.location.Location;
import com.tech.challenge.TechChallenge.domain.user.User;
import com.tech.challenge.TechChallenge.domain.user.UserAuthority;
import com.tech.challenge.TechChallenge.repositories.user.UserRepository;
import com.tech.challenge.TechChallenge.service.location.LocationService;
import com.tech.challenge.TechChallenge.util.HashService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public final UserRepository userRepository;

    public final LocationService locationService;

    public final UserAuthorityService userAuthorityService;

    public UserService(final UserRepository userRepository, final LocationService locationService, final UserAuthorityService userAuthorityService) {
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.userAuthorityService = userAuthorityService;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll -> {}", pageable);

        return userRepository.findAllActive(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(@NotNull final Long id) {

        logger.info("findById -> {}", id);

        return userRepository.findOneActiveById(id);
    }

    public User save(@NotNull final User user) {

        logger.info("created -> {}", user);

        final Location location = Optional.ofNullable(user.getLocation())
                .flatMap(loc -> locationService.findOneByZipCode(loc.getZipCode()))
                .orElseGet(() -> locationService.save(user.getLocation()));

        final List<UserAuthority> authorities = Optional.ofNullable(user.getUserAuthorities())
                .filter(not(Set::isEmpty))
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        user.setUserAuthorities(null);

        Optional.ofNullable(user.getPassword())
                .map(HashService::hash)
                .ifPresent(user::setPassword);

        user.setLocation(location);
        user.setLastModifyDate(Instant.now());

        return Optional.of(userRepository.save(user))
                .map(savedUser -> {
                    userAuthorityService.addAuthoritiesToUser(savedUser, authorities);

                    return savedUser;
                })
                .orElseThrow(() -> new IllegalStateException("User could not be saved"));

    }

    public void delete(@NotNull final Long id) {

        logger.info("delete -> {}", id);

        userRepository.findById(id)
                .ifPresent(user -> {

                    user.setActive(false);

                    userRepository.save(user);

                });
    }


}
