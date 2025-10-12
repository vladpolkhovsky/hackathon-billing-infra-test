package by.faas.billing.mappers;

import java.util.List;
import java.util.Set;
import by.faas.billing.config.security.Role;
import by.faas.billing.dto.UserDto;
import by.faas.billing.jpa.entity.RoleEntity;
import by.faas.billing.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(UserEntity user);

    default Role toRole(RoleEntity role) {
        return role == null ? null : role.getRole();
    }

    Set<RoleEntity> roles(List<Role> roles);

    default RoleEntity toRoleEntity(Role role) {
        return RoleEntity.of(role);
    }
}
