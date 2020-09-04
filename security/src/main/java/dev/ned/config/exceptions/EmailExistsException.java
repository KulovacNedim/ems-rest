package dev.ned.config.exceptions;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String email) {
        super(String.format("Account associated with email %s already exists", email));
    }
}
