package com.example.demo.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    SIMPLE_USER;

    @Override
    public String getAuthority() {
        return toString();
    }
}
