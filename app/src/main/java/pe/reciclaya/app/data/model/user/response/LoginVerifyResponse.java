package pe.reciclaya.app.data.model.user.response;

public class LoginVerifyResponse {
    public static final String VALID = "VALID";
    public static final String INVALID = "INVALID";
    public static final String EXPIRED = "EXPIRED";
    public static final String BLOCKED = "BLOCKED";

    private String result;
    private UserResponse user;

    public LoginVerifyResponse() {}

    public String getResult() { return result; }
    public UserResponse getUser() { return user; }

    public void setResult(String result) { this.result = result; }
    public void setUser(UserResponse user) { this.user = user; }
}
