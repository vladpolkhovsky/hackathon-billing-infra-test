package com.example.demo.service;

import java.util.List;
import com.example.demo.config.security.JwtRelatedThings;
import com.example.demo.config.security.Role;
import com.example.demo.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtRelatedThings jwtRelatedThings;

    public ResponseEntity<UserDto> signIn(String username, String password) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username is empty");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password is empty");
        }

        UserDto user = UserDto.builder()
            .username(username)
            .fio(username + " " + username + " " + username)
            .roles(List.of(Role.SIMPLE_USER))
            .build();

        return ResponseEntity.ok()
            .headers(jwtRelatedThings.createSuccessfulSignInHeaders(user))
            .body(user);
    }

    public UserDetailsService userDetailsService() {
        return username -> UserDto.builder()
            .username(username)
            .fio(username + " " + username + " " + username)
            .roles(List.of(Role.SIMPLE_USER))
            .build();
    }
}
