package by.faas.billing.config.security.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'MANAGER')")
public @interface HasManagerAuthority {
}
