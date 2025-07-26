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
    public Page<Location> findAllActive(@NotNull final Pageable pageable) {

        logger.info("findAllActive -> {}", pageable);

        return this.locationRepository.findAllActive(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Location> findOneActiveById(@NotNull final Long id) {

        logger.info("findOneActiveById -> {}", id);

        return this.locationRepository.findOneActiveById(id);
    }

    public Location save(@NotNull final Location location) {

        logger.info("created -> {}", location);

        final String zipLocation = Optional.ofNullable(location.getZipCode())
                .map(zipCode -> zipCode.replaceAll("[^a-zA-Z0-9]", ""))
                .orElse(null);


        location.setLastModifyDate(Instant.now());
        location.setZipCode(zipLocation);

        return Optional.of(this.locationRepository.save(location))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public void delete(@NotNull final Long id) {

        logger.info("delete -> {}", id);

        locationRepository.findById(id)
                .ifPresent(location -> {

                    location.setActive(false);

                    locationRepository.save(location);

                });

    }

    @Transactional(readOnly = true)
    public Optional<Location> findOneByZipCode(@NotNull final String zipCode) {

        logger.info("findOneByZipCode -> {}", zipCode);

        final String zipLocation = Optional.ofNullable(zipCode)
                .map(code -> code.replaceAll("[^a-zA-Z0-9]", ""))
                .orElse(null);

        return this.locationRepository.findOneByZipCode(zipLocation);
    }
}
