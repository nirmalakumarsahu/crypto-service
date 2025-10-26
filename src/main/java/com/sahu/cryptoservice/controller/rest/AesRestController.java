package com.sahu.cryptoservice.controller.rest;

import com.sahu.cryptoservice.dto.request.AesDecryptionRequest;
import com.sahu.cryptoservice.dto.request.AesEncryptionRequest;
import com.sahu.cryptoservice.dto.response.AesEncryptionResponse;
import com.sahu.cryptoservice.dto.response.ApiResponse;
import com.sahu.cryptoservice.service.AesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crypto/aes")
@RequiredArgsConstructor
public class AesRestController {

    public final AesService aesService;

    @PostMapping("/encrypt-text")
    public ResponseEntity<ApiResponse<AesEncryptionResponse>> encryptText(@Valid @RequestBody AesEncryptionRequest aesEncryptionRequest) {
        return ApiResponse.success(HttpStatus.OK,
                "Text Encrypted Successfully",
                aesService.encryptText(aesEncryptionRequest)
        );
    }

    @PostMapping("/decrypt-text")
    public ResponseEntity<ApiResponse<String>> decryptText(@Valid @RequestBody AesDecryptionRequest aesDecryptionRequest    ) {
        return ApiResponse.success(HttpStatus.OK,
                "Text Decrypted Successfully",
                aesService.decryptText(aesDecryptionRequest)
        );
    }

}
