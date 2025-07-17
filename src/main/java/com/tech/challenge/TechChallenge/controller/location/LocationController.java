package com.tech.challenge.TechChallenge.controller.location;

import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.location.Location;
import com.tech.challenge.TechChallenge.service.location.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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
    public ResponseEntity<Page<Location>> findAll(@RequestParam("page") final Pageable page) {

        logger.info("GET -> /loc/locations -> {}", page);

        return ResponseEntity.ok(locationService.findAll(page));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @JsonView(Location.Json.Base.class)
    public ResponseEntity<Location> findById(@PathVariable("id") final Long id) {

        logger.info("GET -> /loc/locations/{id} -> {} ", id);

        return ResponseEntity.ok(locationService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @PostMapping
    public ResponseEntity<Location> created(@RequestBody final Location location) {

        logger.info("POST -> loc/locations -> {}", location);

        return ResponseEntity.ok(locationService.created(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable("id") final Long id, @RequestBody final Location location) {

        logger.info("PUT -> loc/locations/{id} -> {}, {} ", id, location);


        return ResponseEntity.ok(locationService.update(location, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {

        logger.info("DELETE -> /loc/locations/{id} -> {}", id);

        locationService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value())
                .build();
    }


}
