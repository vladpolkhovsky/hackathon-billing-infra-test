package by.faas.billing.exception;

import jakarta.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;
import org.springframework.security.authorization.AuthorizationDeniedException;

@UtilityClass
public class JpaExceptionCreator {

    public Supplier<AuthorizationDeniedException> userNotFound(String username) {
        return () -> new AuthorizationDeniedException(("User with username \"%s\" not found")
            .formatted(username));
    }

    public <T extends Serializable> Supplier<EntityNotFoundException> notFound(String entityName, T entityId) {
        return () -> new EntityNotFoundException("%s with id \"%s\" not found"
            .formatted(entityName, entityId.toString()));
    }

}
