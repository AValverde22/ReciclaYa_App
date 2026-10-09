package pe.reciclaya.app.data.model.restablecer.request;

public class RestablecerRequestReset {
    private String email;
    private String password;

    public RestablecerRequestReset() {}

    public RestablecerRequestReset(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
