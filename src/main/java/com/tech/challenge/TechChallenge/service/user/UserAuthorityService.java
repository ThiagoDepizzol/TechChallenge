package com.tech.challenge.TechChallenge.service.user;

import com.tech.challenge.TechChallenge.domain.user.User;
import com.tech.challenge.TechChallenge.domain.user.UserAuthority;
import com.tech.challenge.TechChallenge.repositories.user.UserAuthorityRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthorityService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityService.class);

    public final UserAuthorityRepository userAuthorityRepository;

    public UserAuthorityService(final UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserAuthority> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll -> {}", pageable);

        return userAuthorityRepository.findAllActive(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<UserAuthority> findById(@NotNull final Long id) {

        logger.info("findById -> {}", id);

        return userAuthorityRepository.findOneActiveById(id);
    }

    public void addAuthoritiesToUser(@NotNull final User user, @NotNull final List<UserAuthority> authorities) {

        logger.info("addAuthoritiesToUser -> {}, {}", authorities, user);

        Optional.ofNullable(authorities)
                .orElse(Collections.emptyList())
                .forEach(authority -> {

                    authority.setUser(user);

                    userAuthorityRepository.save(authority);
                });
    }

    public UserAuthority created(@NotNull final UserAuthority authority) {

        logger.info("created -> {}", authority);

        return userAuthorityRepository.save(authority);

    }

    public UserAuthority update(@NotNull final UserAuthority authority, @NotNull final Long id) {

        logger.info("update -> {}", authority);

        return userAuthorityRepository.findById(id)
                .map(bdAuthority -> {

                    authority.setId(bdAuthority.getId());
                    authority.setLastModifyDate(Instant.now());

                    return this.userAuthorityRepository.save(authority);

                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public void delete(@NotNull final Long id) {

        logger.info("delete -> {}", id);

        userAuthorityRepository.findById(id)
                .ifPresent(authority -> {
                    authority.setActive(false);

                    userAuthorityRepository.save(authority);
                });
    }


}
