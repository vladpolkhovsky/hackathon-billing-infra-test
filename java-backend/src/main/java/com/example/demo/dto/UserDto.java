package com.example.demo.dto;

import java.util.Collection;
import java.util.List;
import com.example.demo.config.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
public class UserDto implements UserDetails {
    private String username;
    private String fio;
    private List<Role> roles;
    @JsonIgnore
    private String password;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}
