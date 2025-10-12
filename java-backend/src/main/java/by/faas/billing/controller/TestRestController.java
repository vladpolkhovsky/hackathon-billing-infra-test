package by.faas.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import by.faas.billing.dto.TestBodyResponse;
import by.faas.billing.dto.TestBodyResponse.TestBodyResponsePart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Тестовый контроллер, авторизации нет.")
public class TestRestController {

    @GetMapping("/v1/test")
    @Operation(summary = "Получение данных в виде мапы.")
    public ResponseEntity<Map<String, Object>> test() {
        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "code", 200,
            "timestamp", LocalDateTime.now()
        ));
    }

    @GetMapping("/v1/test-with-body")
    @Operation(summary = "Получение объекта с известной структурой")
    public ResponseEntity<TestBodyResponse> testWithBody() {
        return ResponseEntity.ok(TestBodyResponse.builder()
            .id(System.currentTimeMillis())
            .uuid(UUID.randomUUID())
            .name("Test name " + UUID.randomUUID())
            .parts(List.of(TestBodyResponsePart.builder()
                .partName("part name " + UUID.randomUUID())
                .subPartIds(List.of(1L, 2L, 3L))
                .build()))
            .build());
    }
}
