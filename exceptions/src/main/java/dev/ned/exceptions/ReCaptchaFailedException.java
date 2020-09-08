package dev.ned.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ned.payloads.ApiError;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReCaptchaFailedException extends RuntimeException {
    public ReCaptchaFailedException() {
        super("ReCaptcha identified a bot made request");
    }

    public static void throwLoginReCaptchaException(HttpServletRequest request, HttpServletResponse response) {
        ReCaptchaFailedException ex = new ReCaptchaFailedException();
        response.setStatus(401);
        response.setContentType("application/json");
        try (
                PrintWriter out = response.getWriter()) {
            final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), ex.getMessage());
            out.print(new ObjectMapper().writeValueAsString(apiError));
            out.flush();
        } catch (
                IOException e) {
            throw new InvalidPayloadException();
        }
    }
}
