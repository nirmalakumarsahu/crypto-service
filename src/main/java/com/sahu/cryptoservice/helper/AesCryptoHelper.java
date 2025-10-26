package com.sahu.cryptoservice.helper;

import com.sahu.cryptoservice.constant.AppConstant;
import com.sahu.cryptoservice.constant.SymmetricAlgorithm;
import com.sahu.cryptoservice.exception.DecryptionFailedException;
import com.sahu.cryptoservice.exception.EncryptionFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Component
public class AesCryptoHelper {

    /**
     * Generates a secret key for the specified symmetric algorithm.
     *
     * @param algorithm the symmetric algorithm
     * @return the generated secret key as a Base64 encoded string
     */
    public String generateSecretKey(SymmetricAlgorithm algorithm) {
        Assert.notNull(algorithm, AppConstant.ALGORITHM_TYPE_MUST_NOT_BE_NULL);
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getInstance());
            keyGenerator.init(algorithm.getKeySize());
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            log.error("Error generating secret key: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error generating secret key", e);
        }
    }

    /**
     * Converts a Base64 encoded string to a SecretKey.
     *
     * @param keyString the Base64 encoded key string
     * @return the SecretKey
     */
    private SecretKey generateStringToSecretKey(String keyString, SymmetricAlgorithm algorithm) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, algorithm.getInstance());
    }

    /**
     * Encrypts the given data using the specified secret key and algorithm type.
     *
     * @param data      the data to encrypt
     * @param secretKey the secret key as a Base64 encoded string
     * @param algorithm the symmetric algorithm
     * @return the encrypted data as a Base64 encoded string
     */
    public String encryptText(String data, String secretKey, SymmetricAlgorithm algorithm) {
        Assert.notNull(data, "Data must not be null or empty");
        Assert.notNull(secretKey, "Secret key must not be null or empty");
        Assert.notNull(algorithm, AppConstant.ALGORITHM_TYPE_MUST_NOT_BE_NULL);

        try {
            // Create Cipher instance
            Cipher cipher = Cipher.getInstance(algorithm.getAlgorithm());

            // Generate IV
            byte[] iv = new byte[AppConstant.IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            // Initialize GCM parameter spec
            GCMParameterSpec gcmSpec = new GCMParameterSpec(AppConstant.GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.ENCRYPT_MODE, generateStringToSecretKey(secretKey, algorithm), gcmSpec);

            // Perform encryption
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());

            // Combine IV and encrypted bytes and algorithm
            byte[] combined = new byte[algorithm.getLength() + iv.length + encryptedBytes.length];
            System.arraycopy(algorithm.name().getBytes(), 0, combined, 0, algorithm.getLength());
            System.arraycopy(iv, 0, combined, algorithm.getLength(), iv.length);
            System.arraycopy(encryptedBytes, 0, combined, algorithm.getLength() + iv.length, encryptedBytes.length);

            // Return Base64 encoded string
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            log.error("Error during encryption: {}", e.getMessage());
            throw new EncryptionFailedException("Encryption failed: " + e.getMessage());
        }
    }

    /**
     * Decrypts the given cipher text using the specified secret key and algorithm type.
     *
     * @param cipherText the cipher text to decrypt (Base64 encoded)
     * @param secretKey  the secret key as a Base64 encoded string
     * @param algorithm  the symmetric algorithm
     * @return the decrypted data as a string
     */
    public String decryptText(String cipherText, String secretKey, SymmetricAlgorithm algorithm) {
        Assert.notNull(cipherText, "Cipher text must not be null or empty");
        Assert.notNull(secretKey, "Secret key must not be null or empty");
        Assert.notNull(algorithm, AppConstant.ALGORITHM_TYPE_MUST_NOT_BE_NULL);

        try {
            // Decode Base64 encoded data
            byte[] combined = Base64.getDecoder().decode(cipherText);

            // Verify algorithm prefix
            byte[] algorithmBytes = new byte[algorithm.getLength()];
            System.arraycopy(combined, 0, algorithmBytes, 0, algorithmBytes.length);
            String algorithmPrefix = new String(algorithmBytes);
            if (!algorithmPrefix.equals(algorithm.name())) {
                throw new DecryptionFailedException("Algorithm type mismatch during decryption");
            }

            // Extract IV
            byte[] iv = new byte[AppConstant.IV_LENGTH];
            System.arraycopy(combined, algorithm.getLength(), iv, 0, iv.length);

            // Extract encrypted data
            byte[] encryptedBytes = new byte[combined.length - iv.length - algorithm.getLength()];
            System.arraycopy(combined, algorithm.getLength() + iv.length, encryptedBytes, 0, encryptedBytes.length);

            // Initialize cipher in decryption mode
            Cipher cipher = Cipher.getInstance(algorithm.getAlgorithm());
            GCMParameterSpec gcmSpec = new GCMParameterSpec(AppConstant.GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, generateStringToSecretKey(secretKey, algorithm), gcmSpec);

            // Perform decryption
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error("Error during decryption: {}", e.getMessage());
            throw new DecryptionFailedException(e.getMessage());
        }
    }

}