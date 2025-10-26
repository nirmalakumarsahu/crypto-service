package com.sahu.cryptoservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AsymmetricAlgorithm {
    RSA2048("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "RSA", 2408),
    RSA4096("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "RSA", 4096);

    private final String algorithm;
    private final String instance;
    private final int keySize;
    
}