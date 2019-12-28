package dev.ned.config;

import com.auth0.jwt.JWT;
import dev.ned.model.RefreshToken;
import dev.ned.model.User;
import dev.ned.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JwtTokenProvider {

    UserRepository userRepository;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected String createJwtToken(UserPrincipal principal, boolean hasExpireAt) {
        String token;
        if (hasExpireAt) {
            token = JWT.create()
                    .withSubject(principal.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                    .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        } else {
            token = JWT.create()
                    .withSubject(principal.getUsername())
                    .withClaim("random", getRandomString(30))
                    .sign(HMAC512(JwtProperties.SECRET.getBytes()));

            // store refresh token in db
            User user = userRepository.findByEmail(principal.getUsername());
            user.setRefreshToken(new RefreshToken(token));
            this.userRepository.save(user);
        }

        return token;
    }

    protected void setHeader(HttpServletResponse response, String name, String value) {
        response.setHeader(name, value);
    }

    protected String getRandomString(int n) {
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString
                = new String(array, StandardCharsets.UTF_8);

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }
}
