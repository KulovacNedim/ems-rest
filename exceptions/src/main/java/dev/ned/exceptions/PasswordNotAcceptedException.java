package dev.ned.exceptions;

public class PasswordNotAcceptedException extends RuntimeException {
    public PasswordNotAcceptedException() {
        super("Invalid password format received.");
    }
}
