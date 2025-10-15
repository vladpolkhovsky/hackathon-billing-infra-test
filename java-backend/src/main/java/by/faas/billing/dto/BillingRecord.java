package by.faas.billing.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BillingRecord {
    private BigDecimal totalCpuAmount;
    private BigDecimal totalCpuPrice;
    private BigDecimal totalMemoryAmount;
    private BigDecimal totalMemoryPrice;
    private BigDecimal totalCallCount;
    private BigDecimal totalCallPrice;
    private BigDecimal totalPrice;
    private Long metricsRecordsCount;
}
