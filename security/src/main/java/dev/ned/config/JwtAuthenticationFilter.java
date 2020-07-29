package dev.ned.config;

import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class JwtAuthenticationFilter
//        extends UsernamePasswordAuthenticationFilter
{
//    private AuthenticationManager authenticationManager;
//    private JwtTokenProvider jwtTokenProvider;
//
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        LoginViewModel credentials = null;
//        try {
//            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                credentials.getEmail(),
//                credentials.getPassword(),
//                new ArrayList<>());
//
//        Authentication auth = authenticationManager.authenticate(authenticationToken);
//        return auth;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
//
//        String accessJwtToken = jwtTokenProvider.createJwtToken(userPrincipal, true);
//        String refreshJwtToken = jwtTokenProvider.createJwtToken(userPrincipal, false);
//
//        jwtTokenProvider.setHeader(response, JwtProperties.ACCESS_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessJwtToken);
//        jwtTokenProvider.setHeader(response, JwtProperties.REFRESH_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + refreshJwtToken);
//    }
}
