package dev.ned.config.exceptions;

public class EmailCouldNotBeSentException extends RuntimeException {
    public EmailCouldNotBeSentException(String email) {
        super(String.format("We could not send email to following address: %s", email));
    }
}
