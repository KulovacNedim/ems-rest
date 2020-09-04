package dev.ned.config.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CorsFilter implements Filter {
    @Value("#{'${cors.origins}'.split(',')}")
    List<String> origins;

    @Value("${cors.max-age}")
    String maxAge;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("origin");
        if (origins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Access-Control-Max-Age", maxAge);
        }
        chain.doFilter(req, res);
    }
}
