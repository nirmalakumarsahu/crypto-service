package com.sahu.cryptoservice.exception;

public class DecryptionFailedException extends RuntimeException {
    public DecryptionFailedException(String message) {
        super(message);
    }
}
