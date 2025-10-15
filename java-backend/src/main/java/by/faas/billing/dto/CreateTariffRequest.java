package by.faas.billing.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTariffRequest {
    private String tariffName;
    private BigDecimal cpuPrice;
    private BigDecimal memoryPrice;
    private BigDecimal callPrice;
}
