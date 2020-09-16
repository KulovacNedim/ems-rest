package dev.ned.exceptions;

public class PasswordConfirmationException extends RuntimeException {
    public PasswordConfirmationException(String msg) {
        super(msg);
    }
}
