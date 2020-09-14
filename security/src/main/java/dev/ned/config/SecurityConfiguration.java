package dev.ned.config;

import dev.ned.config.filters.CorsFilter;
import dev.ned.config.filters.JwtAuthenticationFilter;
import dev.ned.config.filters.JwtAuthorizationFilter;
import dev.ned.config.services.AuthService;
import dev.ned.config.services.UserPrincipalDetailService;
import dev.ned.recaptcha.services.UserService;
import dev.ned.config.util.JwtUtil;
import dev.ned.exceptions.UnauthorizedAccessHandler;
import dev.ned.recaptcha.services.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    CorsFilter corsFilter;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    CaptchaService captchaService;
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    private UnauthorizedAccessHandler unauthorizedAccessHandler;

    private UserPrincipalDetailService userPrincipalDetailService;

    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService) {
        this.userPrincipalDetailService = userPrincipalDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedAccessHandler)
                .and()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()

                .antMatchers("/nice").hasRole("CEO")
                .antMatchers("/users").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
                .ignoring()
                .antMatchers(HttpMethod.POST, "/api/auth/signup");
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil, captchaService, authService, userService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");
        return jwtAuthenticationFilter;
    }

    private JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager(), userService, jwtUtil);
        return jwtAuthorizationFilter;
    }
}