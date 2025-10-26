package com.sahu.cryptoservice.exception;

public class EncryptionFailedException extends RuntimeException {
    public EncryptionFailedException(String message) {
        super(message);
    }
}
