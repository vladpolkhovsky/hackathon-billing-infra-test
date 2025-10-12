package by.faas.billing.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BackendError {
    private String uri;
    private String method;
    private String message;
    private String stacktrace;
    private String causeMessage;
    private LocalDateTime timestamp;
}
