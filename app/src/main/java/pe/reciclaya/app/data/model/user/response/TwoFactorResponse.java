package pe.reciclaya.app.data.model.user.response;

public class TwoFactorResponse {
    public static final String UPDATED = "UPDATED";
    public static final String INVALID_PASSWORD = "INVALID_PASSWORD";

    private String result;
    private boolean enabled;

    public TwoFactorResponse() {}

    public String getResult() { return result; }
    public boolean isEnabled() { return enabled; }

    public void setResult(String result) { this.result = result; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
