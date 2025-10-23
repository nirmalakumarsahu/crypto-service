package com.sahu.cryptoservice.service;

import com.sahu.cryptoservice.dto.ResourceResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CryptoService {
    ResourceResponse generateEncryptedFile(MultipartFile file);
}
