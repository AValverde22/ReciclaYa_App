package pe.reciclaya.app.data.model.user.request.seguridad;

public class TwoFactorRequest {
    private boolean enabled;
    private String password;

    public TwoFactorRequest() {}
    public TwoFactorRequest(boolean enabled, String password) {
        this.enabled = enabled;
        this.password = password;
    }

    public boolean isEnabled() { return enabled; }
    public String getPassword() { return password; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setPassword(String password) { this.password = password; }
}
