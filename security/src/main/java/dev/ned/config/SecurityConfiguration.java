package dev.ned.config;

import dev.ned.config.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
@EnableWebSecurity
public class SecurityConfiguration
        extends WebSecurityConfigurerAdapter
{

    @Autowired
    private MyUserDetailsService myUserDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService);
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("pass")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("pass")
//                .roles("ADMIN");
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(("/authenticate")).permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }















    //    @Value("${cors.origin}")
//    private String origin;
//
//    private UserPrincipalDetailService userPrincipalDetailService;
//    private UserRepository userRepository;
//    private JwtTokenProvider jwtTokenProvider;
//
//    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
//        this.userPrincipalDetailService = userPrincipalDetailService;
//        this.userRepository = userRepository;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth. (authenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin()
//                .usernameParameter("email")
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), this.jwtTokenProvider))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository, jwtTokenProvider))
//                .authorizeRequests()
//                .antMatchers("/login", "/auth/login").permitAll()
//                .antMatchers("/nice").hasRole("CEO")
//                .and()
//                .headers()
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", origin))
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "Authorization, RefreshToken"));
//    }
//
//    @Bean
//    DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}