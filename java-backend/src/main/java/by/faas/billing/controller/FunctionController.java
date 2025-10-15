package by.faas.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import by.faas.billing.dto.FunctionDto;
import by.faas.billing.jpa.entity.BaseEntity;
import by.faas.billing.service.FunctionService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/function")
@Tag(name = "Функции")
@RequiredArgsConstructor
public class FunctionController {

    private final FunctionService functionService;

    @PageableAsQueryParam
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение списка функций")
    public ResponseEntity<Page<FunctionDto>> getFunction(@ParameterObject
                                                         @PageableDefault(size = 50, sort = BaseEntity.Fields.createdAt, direction = Sort.Direction.DESC)
                                                         Pageable pageable) {
        return ResponseEntity.ok(functionService.list(pageable));
    }
}
