package dev.ned.exceptions;

public class PasswordNotAcceptedException extends RuntimeException {
    public PasswordNotAcceptedException(String msg) {
        super(String.format("Invalid password received: %s", msg));
    }
}
