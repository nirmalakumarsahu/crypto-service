package com.sahu.cryptoservice.service.impl;

import com.sahu.cryptoservice.dto.request.AesDecryptionRequest;
import com.sahu.cryptoservice.dto.request.AesEncryptionRequest;
import com.sahu.cryptoservice.dto.response.AesEncryptionResponse;
import com.sahu.cryptoservice.helper.AesCryptoHelper;
import com.sahu.cryptoservice.service.AesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class AesServiceImpl implements AesService {

    private final AesCryptoHelper aesCryptoHelper;

    @Override
    public AesEncryptionResponse encryptText(AesEncryptionRequest aesEncryptionRequest) {
        Assert.notNull(aesEncryptionRequest, "Encryption request must not be null");

        String secretKey = aesCryptoHelper.generateSecretKey(aesEncryptionRequest.algorithm());
        String cipherText = aesCryptoHelper.encryptText(aesEncryptionRequest.data(), secretKey, aesEncryptionRequest.algorithm());

        return AesEncryptionResponse.builder()
                .cipherText(cipherText)
                .secretKey(secretKey)
                .algorithm(aesEncryptionRequest.algorithm())
                .build();
    }

    @Override
    public String decryptText(AesDecryptionRequest aesDecryptionRequest) {
        Assert.notNull(aesDecryptionRequest, "Decryption request must not be null");

        return aesCryptoHelper.decryptText(aesDecryptionRequest.cipherText(),
                aesDecryptionRequest.secretKey(), aesDecryptionRequest.algorithm());
    }


}
