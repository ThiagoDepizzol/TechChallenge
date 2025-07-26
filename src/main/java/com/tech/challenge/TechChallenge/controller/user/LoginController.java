package com.tech.challenge.TechChallenge.controller.user;

import com.tech.challenge.TechChallenge.domain.user.dto.UserLoginDTO;
import com.tech.challenge.TechChallenge.domain.user.dto.UserResetPasswordDTO;
import com.tech.challenge.TechChallenge.service.user.UserService;
import com.tech.challenge.TechChallenge.utils.MessageResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    public LoginController(final UserService userService) {
        this.userService = userService;
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
