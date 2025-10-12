package by.faas.billing.dto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import by.faas.billing.config.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
public class UserDto implements UserDetails {
    private UUID id;
    private String username;
    private List<Role> roles;

    @JsonIgnore
    private String password;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    private String createdAt;
    private String updatedAt;
}
