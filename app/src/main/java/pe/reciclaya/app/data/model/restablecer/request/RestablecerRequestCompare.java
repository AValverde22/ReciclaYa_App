package pe.reciclaya.app.data.model.restablecer.request;

public class RestablecerRequestCompare {
    private String email;
    private int code;

    public RestablecerRequestCompare() {}

    public RestablecerRequestCompare(String email, int code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() { return email; }
    public int getCode() { return code; }

    public void setEmail(String email) { this.email = email; }
    public void setCode(int code) { this.code = code; }
}
