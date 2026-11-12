package pe.reciclaya.app.data.model.user.request.restablecer;

public class RestablecerRequestReset {
    private String email;
    private String password;
    private int code;

    public RestablecerRequestReset() {}

    public RestablecerRequestReset(String email, String password, int code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public int getCode() { return code; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setCode(int code) { this.code = code; }
}
