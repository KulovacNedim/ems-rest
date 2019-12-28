package dev.ned.config;

import dev.ned.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${cors.origin}")
    private String origin;

    private UserPrincipalDetailService userPrincipalDetailService;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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
                .formLogin()
                .usernameParameter("email")
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), this.jwtTokenProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository, jwtTokenProvider))
                .authorizeRequests()
                .antMatchers("/login", "/auth/login").permitAll()
                .antMatchers("/users").hasRole("CEO")
                .and()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", origin))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "Authorization, RefreshToken"));
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
}