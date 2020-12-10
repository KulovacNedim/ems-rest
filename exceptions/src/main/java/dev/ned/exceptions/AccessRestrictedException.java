package dev.ned.exceptions;

public class AccessRestrictedException extends RuntimeException {
    public AccessRestrictedException() {
        super("Access to the system restricted.");
    }

}
