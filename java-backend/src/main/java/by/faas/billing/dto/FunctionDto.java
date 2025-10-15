package by.faas.billing.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FunctionDto {
    private UUID id;
    private String name;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
