package dev.ned.config.payload;

public class AuthenticationRequest {

    private String email;
    private String password;
    private String reCaptchaToken;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String password, String reCaptchaToken) {
        this.email = email;
        this.password = password;
        this.reCaptchaToken = reCaptchaToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReCaptchaToken() {
        return reCaptchaToken;
    }

    public void setReCaptchaToken(String reCaptchaToken) {
        this.reCaptchaToken = reCaptchaToken;
    }
}
