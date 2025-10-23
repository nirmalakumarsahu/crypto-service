package com.sahu.cryptoservice.helper;

import com.sahu.cryptoservice.constant.AesConstant;
import com.sahu.cryptoservice.constant.FileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@Component
public class AesFileCryptoHelper {

    public String generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AesConstant.ALGORITHM_INSTANCE);
            keyGenerator.init(AesConstant.AES_KEY_SIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            log.error("Error generating secret key: {}", e.getMessage(), e);
            return null;
        }
    }

    private SecretKey generateStringToSecretKey(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, AesConstant.ALGORITHM_INSTANCE);
    }

    private GCMParameterSpec generateIv() {
        byte[] iv = new byte[AesConstant.GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return new GCMParameterSpec(AesConstant.GCM_TAG_LENGTH * 8, iv);
    }

    public File getAesKeyFile(String secretKeyString, String fileName) {
        Assert.notNull(secretKeyString, "Secret key must not be null or empty");
        Assert.notNull(fileName, "File name must not be null or empty");

        try {
            File keyFile = new File(fileName + FileConstant.KEY_FILE_SUFFIX.getValue());
            try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                fos.write(secretKeyString.getBytes());
            }
            return keyFile;
        } catch (Exception e) {
            log.error("Error creating AES key file: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error creating AES key file", e);
        }
    }

    public File ecryptFile(MultipartFile file, String secretKeyString) {
        Assert.notNull(file, "File must not be null");
        Assert.notNull(secretKeyString, "Secret key must not be null or empty");

        try {
            Cipher cipher = Cipher.getInstance(AesConstant.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateStringToSecretKey(secretKeyString), generateIv());

            // Create a temporary file
            log.info("Starting encryption for file: {}", file.getOriginalFilename());
            File encryptedFile = new  File(Objects.requireNonNull(file.getOriginalFilename()) + FileConstant.ENCRYPTED_FILE_SUFFIX.getValue());

            try (InputStream inputStream = file.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(encryptedFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byte[] output = cipher.update(buffer, 0, bytesRead);
                    if (output != null) outputStream.write(output);
                }

                byte[] finalBytes = cipher.doFinal();
                if (finalBytes != null) outputStream.write(finalBytes);
            }

            return encryptedFile;
        }
        catch (Exception e) {
            log.error("Error encrypting file: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error encrypting file", e);
        }
    }

}