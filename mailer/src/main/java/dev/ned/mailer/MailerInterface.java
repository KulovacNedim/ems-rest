package dev.ned.mailer;

import dev.ned.helpers.EmailTypeIdentifierEnum;

import java.io.IOException;
import java.util.Map;

public interface MailerInterface {
    public Integer sendPersonalizedEmail(String email, Map<String, String> data, EmailTypeIdentifierEnum identifier) throws IOException;
}
