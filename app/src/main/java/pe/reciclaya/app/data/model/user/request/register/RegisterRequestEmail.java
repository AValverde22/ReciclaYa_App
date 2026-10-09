package pe.reciclaya.app.data.model.user.request.register;

public class RegisterRequestEmail {
    private String email;

    public RegisterRequestEmail() {}
    public RegisterRequestEmail(String email) { this.email = email; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
