package com.sahu.cryptoservice.dto;

import lombok.Builder;
import org.springframework.core.io.Resource;

@Builder
public record ResourceResponse(
        Resource resource,
        String fileName
) {
}
