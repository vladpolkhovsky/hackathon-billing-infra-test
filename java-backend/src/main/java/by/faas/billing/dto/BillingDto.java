package by.faas.billing.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillingDto extends BillingRecord {
    private FunctionDto function;
    private TariffDto tariff;

    private LocalDateTime billingFrom;
    private LocalDateTime billingTo;

    private Long stepsCount;
    private Map<LocalDateTime, BillingRecordStepDto> steps;
    private BillingPeriod stepPeriod;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class BillingRecordStepDto extends BillingRecord {
        private LocalDateTime stepTime;
        private BillingPeriod stepPeriod;
    }
}
