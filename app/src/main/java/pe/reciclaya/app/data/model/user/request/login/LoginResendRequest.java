package pe.reciclaya.app.data.model.user.request.login;

public class LoginResendRequest {
    private String email;
    private String challenge;

    public LoginResendRequest() {}
    public LoginResendRequest(String email, String challenge) {
        this.email = email;
        this.challenge = challenge;
    }

    public String getEmail() { return email; }
    public String getChallenge() { return challenge; }

    public void setEmail(String email) { this.email = email; }
    public void setChallenge(String challenge) { this.challenge = challenge; }
}
