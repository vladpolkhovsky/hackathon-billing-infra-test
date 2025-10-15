package by.faas.billing.jpa.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import by.faas.billing.jpa.entity.MetricEntity;
import org.springframework.beans.factory.annotation.Value;

public interface ResourceConsumptionProjection {
    @Value("#{target.functionId}")
    UUID getFunctionId();
    @Value("#{target.type}")
    MetricEntity.MetricType getType();
    @Value("#{target.timePeriod}")
    LocalDateTime getTime();
    @Value("#{target.avgValue}")
    BigDecimal getAverage();
    @Value("#{target.minValue}")
    BigDecimal getMin();
    @Value("#{target.maxValue}")
    BigDecimal getMax();
    @Value("#{target.sumValue}")
    BigDecimal getSum();
}
