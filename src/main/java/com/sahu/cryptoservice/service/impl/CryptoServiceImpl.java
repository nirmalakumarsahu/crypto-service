package com.sahu.cryptoservice.service.impl;

import com.sahu.cryptoservice.dto.ResourceResponse;
import com.sahu.cryptoservice.helper.AesFileCryptoHelper;
import com.sahu.cryptoservice.helper.FileWrapperHelper;
import com.sahu.cryptoservice.service.CryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final AesFileCryptoHelper aesFileCryptoHelper;
    private final FileWrapperHelper fileWrapperHelper;

    @Override
    public ResourceResponse generateEncryptedFile(MultipartFile file) {
        log.info("Generating encrypted file for: {}", file.getOriginalFilename());
        String secretKey = aesFileCryptoHelper.generateSecretKey();
        log.debug("Generated Secret Key: {}", secretKey);

        File wrappedFile = null;
        File aesKeyFile = null;
        File encryptedFile = null;
        try {
            aesKeyFile = aesFileCryptoHelper.getAesKeyFile(secretKey, file.getOriginalFilename());
            encryptedFile = aesFileCryptoHelper.ecryptFile(file, secretKey);

            //Generate GZIP file containing both encrypted file and key file
            if (Objects.nonNull(aesKeyFile) && Objects.nonNull(encryptedFile)) {
                log.info("Successfully generated encrypted file and key file.");
                log.debug("Encrypted File: {}, Key File: {}", encryptedFile.getName(), aesKeyFile.getName());
                wrappedFile = fileWrapperHelper.wrapFilesAsGzip(List.of(encryptedFile, aesKeyFile), encryptedFile.getName());
            }

            ResourceResponse response =  ResourceResponse.builder()
                    .resource(new FileSystemResource(wrappedFile))
                    .fileName(wrappedFile.getName())
                    .build();
            log.info("Successfully generated wrapped GZIP file: {}", wrappedFile.getName());
            return response;
        } catch (Exception e) {
            log.error("Error generating encrypted file: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Failed to generate encrypted file", e);
        } finally {
            try {
                if (Objects.nonNull(encryptedFile) && encryptedFile.exists()) {
                    Files.delete(encryptedFile.toPath());
                }
                if (Objects.nonNull(aesKeyFile) && aesKeyFile.exists()) {
                    Files.delete(aesKeyFile.toPath());
                }
                log.info("Temporary files cleaned up successfully.");
            } catch (Exception e) {
                log.warn("Error during cleanup of temporary files: {}", e.getMessage(), e);
            }
        }
    }

}
