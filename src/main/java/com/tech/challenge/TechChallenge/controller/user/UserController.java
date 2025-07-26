package com.tech.challenge.TechChallenge.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.user.User;
import com.tech.challenge.TechChallenge.service.user.UserService;
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
@RequestMapping("usr/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    @JsonView(User.Json.All.class)
    public ResponseEntity<List<User>> findAll(final Pageable page) {

        logger.info("GET -> /usr/users -> {}", page);

        return ResponseEntity.ok(userService.findAll(page)
                .map(user -> user.loadAuthorities().loadLocation()).getContent());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @JsonView(User.Json.All.class)
    public ResponseEntity<User> findById(@PathVariable("id") final Long id) {

        logger.info("GET -> /usr/users/{id} -> {} ", id);

        return ResponseEntity.ok(userService.findById(id)
                .map(user -> user.loadLocation()
                        .loadAuthorities())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @PostMapping
    @JsonView(User.Json.All.class)
    public ResponseEntity<User> created(@RequestBody final User user) {

        logger.info("POST -> usr/users -> {}", user);

        return ResponseEntity.ok(userService.save(user)
                .loadLocation()
                .loadAuthorities());
    }

    @PutMapping
    @JsonView(User.Json.All.class)
    public ResponseEntity<User> update(@RequestBody final User user) {

        logger.info("PUT -> usr/users/{id} -> {}", user);

        return user.hasId() ?
                created(user) :
                ResponseEntity.ok(userService.save(user)
                        .loadLocation()
                        .loadAuthorities());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {

        logger.info("DELETE -> /usr/users/{id} -> {}", id);

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @GetMapping("/teste")
    public String test() {
        return "OK!";
    }

}
