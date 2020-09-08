package dev.ned.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.exceptions.InvalidPayloadException;
import dev.ned.exceptions.ReCaptchaFailedException;
import dev.ned.recaptcha.services.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CaptchaFilter implements Filter {
    @Autowired
    CaptchaService captchaService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AuthenticationRequest requestPayload = null;
        try {
            requestPayload = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
        } catch (IOException e) {
            throw new InvalidPayloadException();
        }

        boolean captchaVerified = captchaService.verify(requestPayload.getReCaptchaToken());
        if (!captchaVerified) {
            ReCaptchaFailedException ex = new ReCaptchaFailedException();
            response.setStatus(401);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new ObjectMapper().writeValueAsString(ex.getMessage()));
            out.flush();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
