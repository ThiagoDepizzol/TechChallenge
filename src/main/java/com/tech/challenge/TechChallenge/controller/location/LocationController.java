package com.tech.challenge.TechChallenge.controller.location;

import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.location.Location;
import com.tech.challenge.TechChallenge.service.location.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("loc/locations")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    @JsonView(Location.Json.Base.class)
    public ResponseEntity<List<Location>> findAll(final Pageable pageable) {

        logger.info("GET -> /loc/locations -> {}", pageable);

        return ResponseEntity.ok(locationService.findAllActive(pageable).getContent());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @JsonView(Location.Json.Base.class)
    public ResponseEntity<Location> findById(@PathVariable("id") final Long id) {

        logger.info("GET -> /loc/locations/{id} -> {} ", id);

        return ResponseEntity.ok(locationService.findOneActiveById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @PostMapping
    public ResponseEntity<Location> created(@RequestBody final Location location) {

        logger.info("POST -> loc/locations -> {}", location);

        return ResponseEntity.ok(locationService.save(location));
    }

    @PutMapping
    public ResponseEntity<Location> update(@RequestBody final Location location) {

        logger.info("PUT -> loc/locations/{id} -> {} ", location);


        return location.hasId() ?
                created(location) :
                ResponseEntity.ok(locationService.save(location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {

        logger.info("DELETE -> /loc/locations/{id} -> {}", id);

        locationService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value())
                .build();
    }


}
