package dev.ned.exceptions;

public class EmailNotConfirmedException extends RuntimeException {
    public EmailNotConfirmedException() {
        super("email not confirmed. Check your email for confirmation link.");
    }
}
