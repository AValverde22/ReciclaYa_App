package pe.reciclaya.app.data.model.user.request.login;

public class LoginVerifyRequest {
    private String email;
    private String challenge;
    private String code;

    public LoginVerifyRequest() {}
    public LoginVerifyRequest(String email, String challenge, String code) {
        this.email = email;
        this.challenge = challenge;
        this.code = code;
    }

    public String getEmail() { return email; }
    public String getChallenge() { return challenge; }
    public String getCode() { return code; }

    public void setEmail(String email) { this.email = email; }
    public void setChallenge(String challenge) { this.challenge = challenge; }
    public void setCode(String code) { this.code = code; }
}
