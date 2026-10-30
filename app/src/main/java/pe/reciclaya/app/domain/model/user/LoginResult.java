package pe.reciclaya.app.domain.model.user;

// Resultado del inicio de sesión: acceso concedido o código de verificación pendiente (2FA)
public class LoginResult {
    private final UserRole role;
    private final String email;
    private final String challenge;

    private LoginResult(UserRole role, String email, String challenge) {
        this.role = role;
        this.email = email;
        this.challenge = challenge;
    }

    public static LoginResult accesoConcedido(UserRole role) {
        return new LoginResult(role, null, null);
    }

    public static LoginResult codigoRequerido(String email, String challenge) {
        return new LoginResult(null, email, challenge);
    }

    public boolean requiereCodigo() { return challenge != null; }
    public UserRole getRole() { return role; }
    public String getEmail() { return email; }
    public String getChallenge() { return challenge; }
}
