package com.sahu.cryptoservice.service;

import com.sahu.cryptoservice.dto.request.AesDecryptionRequest;
import com.sahu.cryptoservice.dto.request.AesEncryptionRequest;
import com.sahu.cryptoservice.dto.response.AesEncryptionResponse;

public interface AesService {
    AesEncryptionResponse encryptText(AesEncryptionRequest aesEncryptionRequest);

    String decryptText(AesDecryptionRequest aesDecryptionRequest);
}
