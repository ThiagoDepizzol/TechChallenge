package com.tech.challenge.TechChallenge.controller.user;

import com.tech.challenge.TechChallenge.domain.user.dto.UserLoginDTO;
import com.tech.challenge.TechChallenge.domain.user.dto.UserResetPasswordDTO;
import com.tech.challenge.TechChallenge.service.user.LoginService;
import com.tech.challenge.TechChallenge.utils.MessageResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    private final AuthenticationManager authenticationManager;

    public LoginController(final LoginService loginService, final AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final UserLoginDTO dto) {

        logger.info("POST -> usr/users/login -> {} ", dto);

        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());

        try {

            final Authentication authentication = authenticationManager.authenticate(loginToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new MessageResponseDTO("Usuário logado com sucesso"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponseDTO("Usuário ou senha inválidos"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDTO> resetPassword(@RequestBody final UserResetPasswordDTO dto) {

        logger.info("POST -> usr/users/reset-password -> {} ", dto);

        loginService.resetPassword(dto);

        return ResponseEntity.ok(new MessageResponseDTO("Senha alterada com sucesso"));
    }


}
