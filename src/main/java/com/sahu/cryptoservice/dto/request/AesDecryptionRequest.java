package com.sahu.cryptoservice.dto.request;

import com.sahu.cryptoservice.constant.SymmetricAlgorithm;
import jakarta.validation.constraints.NotNull;

public record AesDecryptionRequest(
        @NotNull(message = "Cipher text to decrypt cannot be null")
        String cipherText,
        @NotNull(message = "Secret key cannot be null")
        String secretKey,
        @NotNull(message = "Algorithm cannot be null")
        SymmetricAlgorithm algorithm
) {
}
