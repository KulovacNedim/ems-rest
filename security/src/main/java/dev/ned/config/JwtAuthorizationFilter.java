package dev.ned.config;

public class JwtAuthorizationFilter
//        extends BasicAuthenticationFilter
{
//    private UserRepository userRepository;
//    private JwtTokenProvider jwtTokenProvider;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
//        super(authenticationManager);
//        this.userRepository = userRepository;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING);
//        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        Authentication authentication = getUsernamePasswordAuthentication(request, response);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        chain.doFilter(request, response);
//    }
//
//    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING)
//                .replace(JwtProperties.TOKEN_PREFIX, "");
//
//        if (!token.equals("")) {
//            try {
//                return getAuthentication(token, response);
//            } catch (TokenExpiredException exc) {
//                String refreshToken = request.getHeader(JwtProperties.REFRESH_TOKEN_HEADER_STRING)
//                        .replace(JwtProperties.TOKEN_PREFIX, "");
//                if (!refreshToken.equals("")) {
//                    return getAuthentication(refreshToken, response);
//                }
//            }
//        }
//        return null;
//    }
//
//    private Authentication getAuthentication(String token, HttpServletResponse response) {
//        DecodedJWT decodedJwt = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
//                .build()
//                .verify(token);
//        String email = decodedJwt.getSubject();
//
//        if (email != null) {
//            Optional<User> userOptional = userRepository.findByEmail(email);
//            User user;
//            if (userOptional.isPresent()) {
//                user = userOptional.get();
//                UserPrincipal principal = new UserPrincipal(user);
//                if (user.getRefreshToken() == null) return null;
//                if (isRefreshToken(user, token)) {
//                    resetTokens(principal, response);
//                }
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
//                return auth;
//            }
//        }
//        return null;
//    }
//
//    private boolean isRefreshToken(User user, String token) {
//        return user.getRefreshToken().getRefreshToken().equals(token);
//    }
//
//    private void resetTokens(UserPrincipal principal, HttpServletResponse response) {
//        String accessJwtToken = jwtTokenProvider.createJwtToken(principal, true);
//        String refreshJwtToken = jwtTokenProvider.createJwtToken(principal, false);
//
//        jwtTokenProvider.setHeader(response, JwtProperties.ACCESS_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessJwtToken);
//        jwtTokenProvider.setHeader(response, JwtProperties.REFRESH_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + refreshJwtToken);
//    }
}
