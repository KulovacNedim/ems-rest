package dev.ned.config.util;

public class JwtProperties {
    public static final String SECRET = "YouDontSupposeToKnow";
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_HEADER_STRING = "Authorization";
}
