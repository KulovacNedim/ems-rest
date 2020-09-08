package dev.ned.exceptions;

public class InvalidPayloadException extends RuntimeException {
    public InvalidPayloadException() {
        super("Invalid payload received in request");
    }
}
