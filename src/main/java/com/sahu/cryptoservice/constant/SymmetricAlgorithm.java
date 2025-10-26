package com.sahu.cryptoservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SymmetricAlgorithm {
    AES128("AES/GCM/NoPadding", "AES", 128, 6),
    AES256("AES/GCM/NoPadding", "AES", 256, 6);

    private final String algorithm;
    private final String instance;
    private final int keySize;
    private final int length;
}