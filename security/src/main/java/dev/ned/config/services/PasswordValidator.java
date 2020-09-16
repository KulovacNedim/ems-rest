package dev.ned.config.services;

import java.util.function.Function;

import static dev.ned.config.services.PasswordValidator.ValidationResult.*;

public interface PasswordValidator extends Function<String, PasswordValidator.ValidationResult> {

    static PasswordValidator hasNumbers() {
        return s -> s.trim().matches("[a-zA-Z ]*\\d+.*") ? SUCCESS : HAS_NO_NUMBERS;
    }

    static PasswordValidator hasLetters() {
        return s -> s.trim().toLowerCase().matches(".*[a-z].*") ? SUCCESS : HAS_NO_LETTERS;
    }

    static PasswordValidator isLongEnough(int num) {
        return s -> s.trim().length() >= num ? SUCCESS : TO_SHORT;
    }

    default PasswordValidator and (PasswordValidator other) {
        return string -> {
            ValidationResult result = this.apply(string);
            return result.equals(SUCCESS) ? other.apply(string) : result;
        };
    }

    enum ValidationResult {
        SUCCESS, HAS_NO_NUMBERS, HAS_NO_LETTERS, TO_SHORT
    }
}
