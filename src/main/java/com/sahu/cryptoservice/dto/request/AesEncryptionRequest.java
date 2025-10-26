package com.sahu.cryptoservice.dto.request;

import com.sahu.cryptoservice.constant.SymmetricAlgorithm;
import jakarta.validation.constraints.NotNull;

public record AesEncryptionRequest(
        @NotNull(message = "Data to encrypt cannot be null")
        String data,
        @NotNull(message = "Algorithm cannot be null")
        SymmetricAlgorithm algorithm
)
{
}
