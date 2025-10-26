package com.sahu.cryptoservice.exception;

import com.sahu.cryptoservice.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EncryptionFailedException.class)
    public ResponseEntity<ApiResponse<String>> handleEncryptionFailedException(EncryptionFailedException encryptionFailedException) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, encryptionFailedException.getMessage(), null);
    }

    @ExceptionHandler(DecryptionFailedException.class)
    public ResponseEntity<ApiResponse<String>> handleDecryptionFailedException(DecryptionFailedException decryptionFailedException) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, decryptionFailedException.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException)
    {
        Map<String, String> fieldErrors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage())
                                .orElse("Invalid Values")
                ));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception exception) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), null);
    }

    private ResponseEntity<ApiResponse<String>> buildErrorResponse(HttpStatus httpStatus, String message, Object error) {
        return ApiResponse.error(
                httpStatus,
                message,
                error
        );
    }

}
