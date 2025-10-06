package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Авторизация")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    @Operation(summary = "Авторизация (сейчас отдаёт то, что написали, чтобы получить токен)")
    public ResponseEntity<UserDto> signIn(@RequestBody SignInRequest body) {
        log.info("sign-in user {}", body);
        return authService.signIn(body.getUsername(), body.getPassword());
    }

    @GetMapping("/iam")
    @Operation(summary = "Получение информации о себе. Закрыт за авторизацией")
    public ResponseEntity<UserDto> iam(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(user);
    }

    @Data
    public static class SignInRequest {
        private String username;
        private String password;
    }
}
