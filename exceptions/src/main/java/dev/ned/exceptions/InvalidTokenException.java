package dev.ned.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid token received");
    }
}
