package com.sahu.cryptoservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sahu.cryptoservice.constant.SymmetricAlgorithm;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AesEncryptionResponse(
        String cipherText,
        String secretKey,
        SymmetricAlgorithm algorithm
) {
}