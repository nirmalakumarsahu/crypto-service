package com.sahu.cryptoservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MacAlgorithm {
    HMACSHA256("HmacSHA256", "HMACSHA", 256),
    HMACSHA512("HmacSHA512", "HMACSHA", 512);

    private final String algorithm;
    private final String instance;
    private final int keySize;
    
}