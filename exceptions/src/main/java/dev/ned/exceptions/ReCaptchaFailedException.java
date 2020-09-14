package dev.ned.exceptions;

public class ReCaptchaFailedException extends RuntimeException {
    public ReCaptchaFailedException() {
        super("ReCaptcha identified a bot made request");
    }
}
