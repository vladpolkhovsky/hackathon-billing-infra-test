package by.faas.billing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import by.faas.billing.dto.BillingDto;
import by.faas.billing.dto.BillingPeriod;
import by.faas.billing.dto.BillingRegistryDto;
import by.faas.billing.dto.FunctionDto;
import by.faas.billing.dto.TariffDto;
import by.faas.billing.exception.JpaExceptionCreator;
import by.faas.billing.jpa.entity.FunctionEntity;
import by.faas.billing.jpa.entity.MetricEntity;
import by.faas.billing.jpa.entity.TariffEntity;
import by.faas.billing.jpa.repository.FunctionRepository;
import by.faas.billing.jpa.repository.ResourceConsumptionProjection;
import by.faas.billing.jpa.repository.TariffRepository;
import by.faas.billing.mappers.FunctionMapper;
import by.faas.billing.mappers.TariffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillingService {

    private static final BigDecimal TO_GB_DIVIDER = BigDecimal.valueOf(1024).pow(3);

    private final FunctionRepository functionRepository;
    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;
    private final FunctionMapper functionMapper;

    @Transactional(readOnly = true)
    public BillingDto getBilling(UUID functionId, UUID tariffId, BillingPeriod period, LocalDateTime from, LocalDateTime to) {
        FunctionEntity function = functionRepository.findById(functionId)
            .orElseThrow(JpaExceptionCreator.notFound("function", functionId));
        TariffEntity tariff = tariffRepository.findById(tariffId)
            .orElseThrow(JpaExceptionCreator.notFound("tariff", tariffId));

        List<ResourceConsumptionProjection> resourceConsumption = functionRepository.getResourceConsumption(period.name(), from, to, functionId);

        Map<MetricEntity.MetricType, List<ResourceConsumptionProjection>> byMetricType = resourceConsumption.stream()
            .collect(Collectors.groupingBy(ResourceConsumptionProjection::getType));
        Map<LocalDateTime, List<ResourceConsumptionProjection>> byTime = resourceConsumption.stream()
            .collect(Collectors.groupingBy(ResourceConsumptionProjection::getTime));

        CalculationResult result = calculateConsumption(byMetricType, tariff, period);

        List<LocalDateTime> steps = resourceConsumption.stream()
            .map(ResourceConsumptionProjection::getTime)
            .distinct()
            .sorted()
            .toList();

        return BillingDto.builder()
            .tariff(tariffMapper.map(tariff))
            .function(functionMapper.map(function))
            .billingFrom(from)
            .billingTo(to)
            .stepPeriod(period)
            .stepsCount((long) steps.size())
            .totalPrice(result.totalPrice())
            .totalMemoryAmount(result.totalMemoryAmount())
            .totalMemoryPrice(result.totalMemoryAmountPrice())
            .totalCpuAmount(result.totalCpuAmount())
            .totalCpuPrice(result.totalCpuAmountPrice())
            .totalCallCount(result.totalRequestAmount())
            .totalCallPrice(result.totalRequestAmountPrice())
            .steps(calcMetricsSteps(steps, byTime, period, tariff))
            .metricsRecordsCount((long) resourceConsumption.size())
            .build();
    }

    public Page<BillingRegistryDto> list(Pageable pageable, UUID tariffId) {
        Page<FunctionEntity> functions = functionRepository.findAll(pageable);

        TariffEntity tariff = tariffRepository.findById(tariffId)
            .orElseThrow(JpaExceptionCreator.notFound("tariff", tariffId));

        List<UUID> functionIds = functions.stream()
            .map(FunctionEntity::getId)
            .toList();

        List<ResourceConsumptionProjection> resourceConsumption = functionRepository.getResourceConsumptionByFunctions(functionIds);

        Map<UUID, List<ResourceConsumptionProjection>> byFunctionId = resourceConsumption.stream()
            .collect(Collectors.groupingBy(ResourceConsumptionProjection::getFunctionId));

        return functions.map(function -> calcRegistryBillingDto(function, byFunctionId.getOrDefault(function.getId(), List.of()), tariff));
    }

    private BillingRegistryDto calcRegistryBillingDto(FunctionEntity function, List<ResourceConsumptionProjection> consumption, TariffEntity tariff) {
        Map<MetricEntity.MetricType, List<ResourceConsumptionProjection>> byMetricType = consumption.stream()
            .collect(Collectors.groupingBy(ResourceConsumptionProjection::getType));

        LocalDateTime billingFromTime = consumption.isEmpty() ? LocalDateTime.now()
            : consumption.getFirst().getTime();

        CalculationResult calculationResult = calculateConsumption(byMetricType, tariff, BillingPeriod.MONTH);

        return BillingRegistryDto.builder()
            .tariff(tariffMapper.map(tariff))
            .billingPeriod(BillingPeriod.MONTH)
            .billingFrom(billingFromTime)
            .metricsRecordsCount((long) consumption.size())
            .totalPrice(calculationResult.totalPrice())
            .totalMemoryAmount(calculationResult.totalMemoryAmount())
            .totalMemoryPrice(calculationResult.totalMemoryAmountPrice())
            .totalCpuAmount(calculationResult.totalCpuAmount())
            .totalCpuPrice(calculationResult.totalCpuAmountPrice())
            .totalCallCount(calculationResult.totalRequestAmount())
            .totalCallPrice(calculationResult.totalRequestAmountPrice())
            .build();
    }

    private Map<LocalDateTime, BillingDto.BillingRecordStepDto> calcMetricsSteps(List<LocalDateTime> steps, Map<LocalDateTime, List<ResourceConsumptionProjection>> byTime, BillingPeriod period, TariffEntity tariff) {
        return steps.stream()
            .collect(Collectors.toMap(Function.identity(), time -> calcStepMetrics(byTime.getOrDefault(time, List.of()), tariff, time, period)));
    }

    private BillingDto.BillingRecordStepDto calcStepMetrics(List<ResourceConsumptionProjection> stepConsumption, TariffEntity tariff, LocalDateTime stepTime, BillingPeriod period) {
        Map<MetricEntity.MetricType, List<ResourceConsumptionProjection>> byType = stepConsumption.stream()
            .collect(Collectors.groupingBy(ResourceConsumptionProjection::getType));

        CalculationResult result = calculateConsumption(byType, tariff, period);

        return BillingDto.BillingRecordStepDto.builder()
            .stepTime(stepTime)
            .stepPeriod(period)
            .totalPrice(result.totalPrice())
            .totalMemoryAmount(result.totalMemoryAmount())
            .totalMemoryPrice(result.totalMemoryAmountPrice())
            .totalCpuAmount(result.totalCpuAmount())
            .totalCpuPrice(result.totalCpuAmountPrice())
            .totalCallCount(result.totalRequestAmount())
            .totalCallPrice(result.totalRequestAmountPrice())
            .metricsRecordsCount((long) stepConsumption.size())
            .build();
    }

    private static CalculationResult calculateConsumption(Map<MetricEntity.MetricType, List<ResourceConsumptionProjection>> byMetricType, TariffEntity tariff, BillingPeriod period) {
        BigDecimal totalMemoryAmount = byMetricType.getOrDefault(MetricEntity.MetricType.memory_usage, List.of()).stream()
            .map(ResourceConsumptionProjection::getAverage)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalMemoryAmountPrice = totalMemoryAmount.multiply(BigDecimal.valueOf(tariff.getMemoryPrice()))
            .divide(getMemoryGbHourDivider(period), RoundingMode.CEILING);

        BigDecimal totalCpuAmount = byMetricType.getOrDefault(MetricEntity.MetricType.cpu_usage, List.of()).stream()
            .map(ResourceConsumptionProjection::getAverage)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCpuAmountPrice = totalCpuAmount.multiply(BigDecimal.valueOf(tariff.getCpuPrice()))
            .divide(getCpuDivider(period), RoundingMode.CEILING);

        BigDecimal totalRequestAmount = byMetricType.getOrDefault(MetricEntity.MetricType.request_count, List.of()).stream()
            .map(ResourceConsumptionProjection::getAverage)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRequestAmountPrice = totalRequestAmount.multiply(BigDecimal.valueOf(tariff.getCallPrice()));

        return new CalculationResult(totalMemoryAmount, totalMemoryAmountPrice, totalCpuAmount, totalCpuAmountPrice, totalRequestAmount, totalRequestAmountPrice);
    }

    private static BigDecimal getMemoryGbHourDivider(BillingPeriod period) {
        BigDecimal divider = BigDecimal.valueOf(1024).pow(3);
        switch (period) {
            case MINUTE -> {
                return divider.multiply(BigDecimal.valueOf(60));
            }
            case HOUR -> {
                return divider;
            }
            case DAY -> {
                return divider.divide(BigDecimal.valueOf(24), RoundingMode.CEILING);
            }
            case MONTH -> {
                return divider.divide(BigDecimal.valueOf(24 * 30), RoundingMode.CEILING);
            }
            case YEAR -> {
                return divider.divide(BigDecimal.valueOf(24 * 30 * 365), RoundingMode.CEILING);
            }
            default -> {
                return divider;
            }
        }
    }

    private static BigDecimal getCpuDivider(BillingPeriod period) {
        BigDecimal divider = BigDecimal.valueOf(1000);
        switch (period) {
            case MINUTE -> {
                return divider.multiply(BigDecimal.valueOf(60));
            }
            case HOUR -> {
                return divider;
            }
            case DAY -> {
                return divider.divide(BigDecimal.valueOf(24), RoundingMode.CEILING);
            }
            case MONTH -> {
                return divider.divide(BigDecimal.valueOf(24 * 30), RoundingMode.CEILING);
            }
            case YEAR -> {
                return divider.divide(BigDecimal.valueOf(24 * 30 * 365), RoundingMode.CEILING);
            }
            default -> {
                return divider;
            }
        }
    }

    private record CalculationResult(BigDecimal totalMemoryAmount, BigDecimal totalMemoryAmountPrice,
                                     BigDecimal totalCpuAmount, BigDecimal totalCpuAmountPrice,
                                     BigDecimal totalRequestAmount, BigDecimal totalRequestAmountPrice) {

        public BigDecimal totalPrice() {
            return totalRequestAmountPrice().add(totalMemoryAmountPrice()).add(totalCpuAmountPrice());
        }
    }
}
