package by.faas.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import by.faas.billing.dto.BillingPeriod;
import by.faas.billing.dto.BillingRecord;
import by.faas.billing.dto.BillingRegistryDto;
import by.faas.billing.jpa.entity.BaseEntity;
import by.faas.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
@Tag(name = "Биллинг")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получения биллингов по функциям")
    public ResponseEntity<Page<BillingRegistryDto>> getFunction(@ParameterObject
                                                                @PageableDefault(size = 20, sort = BaseEntity.Fields.createdAt, direction = Sort.Direction.DESC)
                                                                Pageable pageable,
                                                                @RequestParam("tariffId") UUID tariffId) {
        return ResponseEntity.ok(billingService.list(pageable, tariffId));
    }

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получения детального биллинга по функции")
    public ResponseEntity<BillingRecord> getDetails(@RequestParam("functionId") UUID functionId,
                                                    @RequestParam("tariffId") UUID tariffId,
                                                    @RequestParam("period") BillingPeriod period,
                                                    @Parameter(description = "Время в формате таймстампа c милисекундами")
                                                    @RequestParam("from") Long fromTimestamp,
                                                    @Parameter(description = "Время в формате таймстампа c милисекундами")
                                                    @RequestParam("to") Long toTimestamp) {

        LocalDateTime from = new Date(fromTimestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime to = new Date(toTimestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return ResponseEntity.ok(billingService.getBilling(functionId, tariffId, period, from, to));
    }
}
