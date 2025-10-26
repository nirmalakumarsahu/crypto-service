package com.sahu.cryptoservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HashingAlgorithm {
    SHA256("SHA-256", "SHA", 256),
    SHA512("SHA-512", "SHA", 512);

    private final String algorithm;
    private final String instance;
    private final int keySize;
    
}