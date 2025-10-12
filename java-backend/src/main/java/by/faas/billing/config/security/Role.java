package by.faas.billing.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    SYSTEM_ADMIN,
    MANAGER,
    VIEWER;

    @Override
    public String getAuthority() {
        return toString();
    }
}
