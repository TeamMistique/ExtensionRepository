package com.teammistique.extensionrepository.exceptions;

public class ExpiredJwtException extends Throwable {
    public ExpiredJwtException(String message) {
        super(message);
    }

    public ExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
