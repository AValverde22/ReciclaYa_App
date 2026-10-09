package pe.reciclaya.app.data.model.restablecer.request;

public class RestablecerRequestRecover {
    private String email;

    public RestablecerRequestRecover() {}

    public RestablecerRequestRecover(String email) { this.email = email; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
