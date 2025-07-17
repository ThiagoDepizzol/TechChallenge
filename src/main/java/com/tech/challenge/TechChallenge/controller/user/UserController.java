package com.tech.challenge.TechChallenge.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.user.User;
import com.tech.challenge.TechChallenge.domain.user.dto.UserLoginDTO;
import com.tech.challenge.TechChallenge.domain.user.dto.UserResetPasswordDTO;
import com.tech.challenge.TechChallenge.service.user.UserService;
import com.tech.challenge.TechChallenge.utils.MessageResponseDTO;
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
    public ResponseEntity<Page<User>> findAll(@RequestParam("page") final Pageable page) {

        logger.info("GET -> /usr/users -> {}", page);

        return ResponseEntity.ok(userService.findAll(page)
                .map(User::loadLocation));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @JsonView(User.Json.All.class)
    public ResponseEntity<User> findById(@PathVariable("id") final Long id) {

        logger.info("GET -> /usr/users/{id} -> {} ", id);

        return ResponseEntity.ok(userService.findById(id)
                .map(User::loadLocation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @PostMapping
    @JsonView(User.Json.All.class)
    public ResponseEntity<User> created(@RequestBody final User user) {

        logger.info("POST -> usr/users -> {}", user);

        return ResponseEntity.ok(userService.created(user).loadLocation());
    }

    @PutMapping("/{id}")
    @JsonView(User.Json.All.class)
    public ResponseEntity<User> update(@PathVariable("id") final Long id, @RequestBody final User user) {

        logger.info("PUT -> usr/users/{id} -> {}, {} ", id, user);

        return ResponseEntity.ok(userService.update(user, id).loadLocation());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {

        logger.info("DELETE -> /usr/users/{id} -> {}", id);

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDTO> resetPassword(@RequestBody final UserResetPasswordDTO dto) {

        logger.info("POST -> usr/users/reset-password -> {} ", dto);

        userService.resetPassword(dto);

        return ResponseEntity.ok(new MessageResponseDTO("Senha alterada com sucesso"));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponseDTO> login(@RequestBody final UserLoginDTO dto) {

        logger.info("POST -> usr/users/login -> {} ", dto);

        userService.login(dto);

        return ResponseEntity.ok(new MessageResponseDTO("Usuário logado com sucesso"));
    }


}
