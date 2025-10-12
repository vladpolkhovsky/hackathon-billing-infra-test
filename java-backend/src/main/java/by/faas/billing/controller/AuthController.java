package by.faas.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.file.AccessDeniedException;
import java.util.List;
import by.faas.billing.config.security.Role;
import by.faas.billing.config.security.method.HasManagerAuthority;
import by.faas.billing.dto.UserDto;
import by.faas.billing.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Авторизация (дефолтные admin/admin manager/manager viewer/viewer)")
    public ResponseEntity<UserDto> signIn(@RequestBody SignInRequest body) throws AccessDeniedException {
        log.info("sign-in user {}", body);
        return authService.signIn(body.getUsername(), body.getPassword());
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @HasManagerAuthority
    @Operation(summary = "Создание учётной записи")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequest body, @AuthenticationPrincipal UserDto creator) {
        log.info("sign-up user {}, creator {}", body, creator);
        return authService.sungUp(body.getUsername(), body.getPassword(),
            body.getPasswordConfirmation(), body.getRoles(), creator);
    }

    @GetMapping(value = "/iam", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение информации о себе. Закрыт за авторизацией")
    public ResponseEntity<UserDto> iam(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(user);
    }

    @Data
    public static class SignInRequest {
        private String username;
        private String password;
    }

    @Data
    public static class SignUpRequest {
        private String username;
        private String password;
        private String passwordConfirmation;
        private List<Role> roles;
    }
}
