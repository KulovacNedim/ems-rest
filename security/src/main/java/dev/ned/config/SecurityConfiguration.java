package dev.ned.config;

import dev.ned.config.exceptions.UnauthorizedAccessHandler;
import dev.ned.config.filters.JwtAuthenticationFilter;
import dev.ned.config.filters.JwtAuthorizationFilter;
import dev.ned.config.services.UserPrincipalDetailService;
import dev.ned.config.services.UserService;
import dev.ned.config.util.JwtUtil;
import dev.ned.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    private UnauthorizedAccessHandler unauthorizedAccessHandler;

    private UserPrincipalDetailService userPrincipalDetailService;
    private UserRepository userRepository;

    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService, UserRepository userRepository) {
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.userRepository = userRepository;
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
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()

                .antMatchers("/nice").hasRole("CEO")
                .antMatchers("/users").hasRole("ADMIN")
                .anyRequest().authenticated()
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

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");
        return jwtAuthenticationFilter;
    }

    private JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager(), userService, jwtUtil);
        return jwtAuthorizationFilter;
    }
}