package com.example.demo.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestBodyResponse {
    private Long id;
    private UUID uuid;
    private String name;
    private List<TestBodyResponsePart> parts;

    @Data
    @Builder
    public static class TestBodyResponsePart {
        private String partName;
        private List<Long> subPartIds;
    }
}
