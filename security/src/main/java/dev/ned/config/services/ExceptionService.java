package dev.ned.config.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ned.exceptions.*;
import dev.ned.payloads.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class ExceptionService {

    public static void throwEmailAccountNotConfirmedException(HttpServletRequest request, HttpServletResponse response) {
        EmailNotConfirmedException ex = new EmailNotConfirmedException();
        response.setStatus(400);
        response.setContentType("application/json");
        try (
                PrintWriter out = response.getWriter()) {
            final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
            out.print(new ObjectMapper().writeValueAsString(apiError));
            out.flush();
        } catch (
                IOException e) {
            throw new InvalidPayloadException();
        }
    }

    public static void throwAccessRestrictedException(HttpServletRequest request, HttpServletResponse response) {
        AccessRestrictedException ex = new AccessRestrictedException();
        response.setStatus(403);
        response.setContentType("application/json");
        try (
                PrintWriter out = response.getWriter()) {
            final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
            out.print(new ObjectMapper().writeValueAsString(apiError));
            out.flush();
        } catch (
                IOException e) {
            throw new InvalidPayloadException();
        }
    }

    public static void throwLoginPasswordException(HttpServletRequest request, HttpServletResponse response, String msg) {
        PasswordNotAcceptedException ex = new PasswordNotAcceptedException(msg);
        response.setStatus(400);
        response.setContentType("application/json");
        try (
                PrintWriter out = response.getWriter()) {
            final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
            out.print(new ObjectMapper().writeValueAsString(apiError));
            out.flush();
        } catch (
                IOException e) {
            throw new InvalidPayloadException();
        }
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
