package dev.ned.config;

import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

//    UserRepository userRepository;
//
//    public JwtTokenProvider(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    protected String createJwtToken(UserPrincipal principal, boolean hasExpireAt) {
//        String token;
//        if (hasExpireAt) {
//            token = JWT.create()
//                    .withSubject(principal.getUsername())
//                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
//                    .sign(HMAC512(JwtProperties.SECRET.getBytes()));
//        } else {
//            token = JWT.create()
//                    .withSubject(principal.getUsername())
//                    .withClaim("random", getRandomString(30))
//                    .sign(HMAC512(JwtProperties.SECRET.getBytes()));
//
//            User user = userRepository.findByEmail(principal.getUsername()).get();
//            user.setRefreshToken(new RefreshToken(token));
//            this.userRepository.save(user);
//        }
//
//        return token;
//    }
//
//    protected void setHeader(HttpServletResponse response, String name, String value) {
//        response.setHeader(name, value);
//    }
//
//    protected String getRandomString(int n) {
//        byte[] array = new byte[256];
//        new Random().nextBytes(array);
//        String randomString
//                = new String(array, StandardCharsets.UTF_8);
//
//        StringBuffer result = new StringBuffer();
//
//        for (int k = 0; k < randomString.length(); k++) {
//            char ch = randomString.charAt(k);
//            if (((ch >= 'a' && ch <= 'z')
//                    || (ch >= 'A' && ch <= 'Z')
//                    || (ch >= '0' && ch <= '9'))
//                    && (n > 0)) {
//
//                result.append(ch);
//                n--;
//            }
//        }
//        return result.toString();
//    }
}
