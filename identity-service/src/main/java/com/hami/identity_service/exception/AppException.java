package com.hami.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom application exception to represent domain-specific errors.
 * This exception is immutable and thread-safe.
 */
@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;
    private final int errorCodeValue;
    private final HttpStatus statusCode;

    /**
     * Constructs a new AppException with the specified ErrorCode.
     *
     * @param errorCode the error code representing the specific error
     */
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorCodeValue = errorCode.getCode();
        this.statusCode = errorCode.getStatusCode();
    }

    /**
     * Constructs a new AppException with a custom message.
     *
     * @param errorCode     the error code representing the specific error
     * @param customMessage the custom message for this exception
     */
    public AppException(ErrorCode errorCode, String customMessage) {
        super(customMessage != null ? customMessage : errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorCodeValue = errorCode.getCode();
        this.statusCode = errorCode.getStatusCode();
    }

    /**
     * Constructs a new AppException with a cause.
     *
     * @param errorCode the error code representing the specific error
     * @param cause     the underlying cause of this exception
     */
    public AppException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.errorCodeValue = errorCode.getCode();
        this.statusCode = errorCode.getStatusCode();
    }

    /**
     * Constructs a new AppException with a custom message and a cause.
     *
     * @param errorCode     the error code representing the specific error
     * @param customMessage the custom message for this exception
     * @param cause         the underlying cause of this exception
     */
    public AppException(ErrorCode errorCode, String customMessage, Throwable cause) {
        super(customMessage != null ? customMessage : errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.errorCodeValue = errorCode.getCode();
        this.statusCode = errorCode.getStatusCode();
    }
}
