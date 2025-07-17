package com.tech.challenge.TechChallenge.service.location;

import com.tech.challenge.TechChallenge.domain.location.Location;
import com.tech.challenge.TechChallenge.repositories.location.LocationRepository;
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
import java.util.Optional;

@Service
public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public final LocationRepository locationRepository;

    public LocationService(final LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    public Page<Location> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll -> {}", pageable);

        return this.locationRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Location> findById(@NotNull final Long id) {

        logger.info("findById -> {}", id);

        return this.locationRepository.findById(id);
    }

    public Location created(@NotNull final Location location) {

        logger.info("created -> {}", location);

        location.setLastModifyDate(Instant.now());

        return Optional.of(this.locationRepository.save(location))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    public Location update(@NotNull final Location location, @NotNull final Long id) {

        logger.info("update -> {}", location);

        return locationRepository.findById(id)
                .map(bdLocation -> {

                    location.setId(bdLocation.getId());
                    location.setLastModifyDate(Instant.now());

                    return this.locationRepository.save(location);

                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public void delete(@NotNull final Long id) {

        logger.info("delete -> {}", id);

        locationRepository.findById(id)
                .ifPresent(location -> {

                    location.setActive(false);

                    locationRepository.save(location);

                });

    }
}
