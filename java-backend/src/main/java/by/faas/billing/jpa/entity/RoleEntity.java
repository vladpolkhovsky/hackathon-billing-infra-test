package by.faas.billing.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import by.faas.billing.config.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Data
@Entity
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles", schema = "billing")
public class RoleEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private Role role;

    public static RoleEntity of(Role role) {
        return new RoleEntity(role);
    }
}