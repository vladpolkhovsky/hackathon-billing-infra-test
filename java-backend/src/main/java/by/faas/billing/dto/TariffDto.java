package by.faas.billing.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TariffDto {
    private String id;
    private String name;
    private BigDecimal callPrice;
    private BigDecimal cpuPrice;
    private BigDecimal memoryPrice;
    private UserDto createdBy;
    private String createdAt;
    private String updatedAt;
}
