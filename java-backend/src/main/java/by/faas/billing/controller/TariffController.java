package by.faas.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import by.faas.billing.dto.CreateTariffRequest;
import by.faas.billing.dto.TariffDto;
import by.faas.billing.dto.UserDto;
import by.faas.billing.jpa.entity.BaseEntity;
import by.faas.billing.service.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/tariff")
@Tag(name = "Тарифы")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @PageableAsQueryParam
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение списка тарифов")
    public ResponseEntity<Page<TariffDto>> getFunction(@ParameterObject
                                                       @PageableDefault(size = 50, sort = BaseEntity.Fields.createdAt, direction = Sort.Direction.DESC)
                                                       Pageable pageable) {
        return ResponseEntity.ok(tariffService.list(pageable));
    }

    @Operation(summary = "Создание тарифа")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDto> createTariff(@RequestBody CreateTariffRequest request,
                                                  @AuthenticationPrincipal UserDto creator) {
        return ResponseEntity.ok(tariffService.createTariff(request, creator));
    }

    @Operation(summary = "Удаление тарифа")
    @DeleteMapping(value = "/{tariffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteTariff(@PathVariable UUID tariffId) {
        tariffService.deleteTariff(tariffId);
        return ResponseEntity.ok().build();
    }
}
