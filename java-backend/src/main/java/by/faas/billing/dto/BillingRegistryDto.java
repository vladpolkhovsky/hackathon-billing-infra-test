package by.faas.billing.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BillingRegistryDto extends BillingRecord {
    private FunctionDto function;
    private TariffDto tariff;
    private LocalDateTime billingFrom;
    private BillingPeriod billingPeriod;
}
