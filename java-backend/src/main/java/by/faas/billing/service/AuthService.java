package by.faas.billing.service;

import java.util.List;
import by.faas.billing.config.security.JwtRelatedThings;
import by.faas.billing.config.security.Role;
import by.faas.billing.dto.UserDto;
import by.faas.billing.exception.JpaExceptionCreator;
import by.faas.billing.jpa.entity.UserEntity;
import by.faas.billing.jpa.repository.UserRepository;
import by.faas.billing.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtRelatedThings jwtRelatedThings;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public ResponseEntity<UserDto> signIn(String username, String password) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username is empty");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password is empty");
        }

        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(JpaExceptionCreator.userNotFound(username));

        boolean passwordIncorrect = !passwordEncoder.matches(password, user.getPassword());

        if (passwordIncorrect) {
            throw new AuthorizationDeniedException("invalid password");
        }

        UserDto userDto = userMapper.toDto(user);

        return ResponseEntity.ok()
            .headers(jwtRelatedThings.createSuccessfulSignInHeaders(userDto))
            .body(userDto);
    }

    @Transactional
    public ResponseEntity<UserDto> sungUp(String username, String password, String passwordConfirmation, List<Role> roles, UserDto creator) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username is empty");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password is empty");
        }

        if (CollectionUtils.isEmpty(roles)) {
            throw new IllegalArgumentException("roles is empty");
        }

        if (!StringUtils.equals(password, passwordConfirmation)) {
            throw new IllegalArgumentException("passwords don't match");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("password length must be 6 characters long");
        }

        if (roles.contains(Role.SYSTEM_ADMIN) && !creator.getRoles().contains(Role.SYSTEM_ADMIN)) {
            throw new IllegalArgumentException("only system admins can create user with system admin role");
        }

        UserEntity saved = userRepository.save(UserEntity.builder()
            .roles(userMapper.roles(roles))
            .password(passwordEncoder.encode(password))
            .username(username)
            .build());

        return ResponseEntity.ok(userMapper.toDto(saved));
    }

    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok()
            .headers(jwtRelatedThings.createLogoutHeaders())
            .build();
    }
}
